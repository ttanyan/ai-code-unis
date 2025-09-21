<template>
  <div class="orders">
    <div class="container">
      <!-- 面包屑导航 -->
      <div class="breadcrumb">
        <span @click="$router.push('/')">首页</span> > 
        <span>我的订单</span>
      </div>
      
      <!-- 订单筛选 -->
      <div class="filters">
        <button 
          :class="{ active: currentStatus === 'all' }" 
          @click="filterOrders('all')"
        >
          全部订单
        </button>
        <button 
          :class="{ active: currentStatus === '待付款' }" 
          @click="filterOrders('待付款')"
        >
          待付款
        </button>
        <button 
          :class="{ active: currentStatus === '待发货' }" 
          @click="filterOrders('待发货')"
        >
          待发货
        </button>
        <button 
          :class="{ active: currentStatus === '已发货' }" 
          @click="filterOrders('已发货')"
        >
          已发货
        </button>
        <button 
          :class="{ active: currentStatus === '已完成' }" 
          @click="filterOrders('已完成')"
        >
          已完成
        </button>
      </div>
      
      <!-- 订单列表 -->
      <div class="orders-list" v-if="filteredOrders.length > 0">
        <div 
          class="order-item" 
          v-for="(order, index) in filteredOrders" 
          :key="index"
        >
          <div class="order-header">
            <div class="order-info">
              <p><strong>订单号：</strong>{{ order.id }}</p>
              <p><strong>下单时间：</strong>{{ order.date }}</p>
            </div>
            <div class="order-status" :class="getStatusClass(order.status)">
              {{ order.status }}
            </div>
          </div>
          
          <div class="order-items">
            <div 
              class="item" 
              v-for="(item, itemIndex) in order.items" 
              :key="itemIndex"
              @click="goToProductDetail(item.product.id)"
            >
              <img :src="item.product.image" :alt="item.product.name" />
              <div class="item-info">
                <h4>{{ item.product.name }}</h4>
                <p>¥{{ item.product.price }} × {{ item.quantity }}</p>
              </div>
            </div>
          </div>
          
          <div class="order-footer">
            <div class="total">
              共 {{ getOrderItemCount(order) }} 件商品 合计：<span>¥{{ order.total.toFixed(2) }}</span>
            </div>
            
            <div class="actions">
              <button 
                v-if="order.status === '待付款'" 
                class="pay-btn" 
                @click="payOrder(order)"
              >
                立即付款
              </button>
              <button 
                v-if="order.status === '待发货'" 
                class="cancel-btn" 
                @click="cancelOrder(order)"
              >
                取消订单
              </button>
              <button 
                v-if="order.status === '已发货'" 
                class="confirm-btn" 
                @click="confirmReceipt(order)"
              >
                确认收货
              </button>
              <button 
                v-if="order.status === '已完成'" 
                class="review-btn" 
                @click="reviewOrder(order)"
              >
                评价
              </button>
            </div>
          </div>
        </div>
      </div>
      
      <!-- 无订单提示 -->
      <div class="no-orders" v-else>
        <p>暂无{{ getStatusText(currentStatus) }}订单</p>
        <button @click="$router.push('/products')">去购物</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 当前筛选状态
const currentStatus = ref('all')

// 订单列表
const orders = ref([])

// 组件挂载时从localStorage加载订单数据
onMounted(() => {
  loadOrders()
})

// 加载订单
const loadOrders = () => {
  const savedOrders = localStorage.getItem('orders')
  if (savedOrders) {
    orders.value = JSON.parse(savedOrders)
  }
}

// 保存订单
const saveOrders = () => {
  localStorage.setItem('orders', JSON.stringify(orders.value))
}

// 过滤后的订单
const filteredOrders = computed(() => {
  if (currentStatus.value === 'all') {
    return orders.value
  } else {
    return orders.value.filter(order => order.status === currentStatus.value)
  }
})

// 获取订单中商品数量
const getOrderItemCount = (order) => {
  return order.items.reduce((count, item) => count + item.quantity, 0)
}

// 获取状态对应的文字
const getStatusText = (status) => {
  const statusMap = {
    'all': '',
    '待付款': '待付款',
    '待发货': '待发货',
    '已发货': '已发货',
    '已完成': '已完成'
  }
  return statusMap[status] || ''
}

// 获取状态对应的CSS类
const getStatusClass = (status) => {
  return `status-${status}`
}

// 筛选订单
const filterOrders = (status) => {
  currentStatus.value = status
}

// 支付订单
const payOrder = (order) => {
  if (confirm('确定要支付该订单吗？')) {
    // 更新订单状态
    order.status = '待发货'
    saveOrders()
    alert(`订单 ${order.id} 支付成功！我们将在24小时内为您发货。`)
  }
}

// 取消订单
const cancelOrder = (order) => {
  if (confirm('确定要取消该订单吗？')) {
    // 更新订单状态
    order.status = '已取消'
    saveOrders()
    alert(`订单 ${order.id} 已取消。`)
  }
}

// 确认收货
const confirmReceipt = (order) => {
  if (confirm('确定已经收到货物了吗？')) {
    // 更新订单状态
    order.status = '已完成'
    saveOrders()
    alert(`订单 ${order.id} 已确认收货。感谢您的购买！`)
  }
}

// 评价订单
const reviewOrder = (order) => {
  alert(`订单 ${order.id} 评价功能正在开发中...`)
}

// 导航到商品详情
const goToProductDetail = (id) => {
  router.push(`/product/${id}`)
}
</script>

<style>
.orders {
  min-height: 100vh;
  background-color: #f8f9fa;
}

.container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 2rem 1rem;
}

/* 面包屑 */
.breadcrumb {
  color: #666;
  margin-bottom: 2rem;
  cursor: pointer;
}

.breadcrumb span {
  color: #007bff;
}

.breadcrumb span:last-child {
  color: #666;
  cursor: default;
}

/* 筛选按钮 */
.filters {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
  flex-wrap: wrap;
}

.filters button {
  padding: 0.5rem 1rem;
  border: 1px solid #ddd;
  background-color: white;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.filters button.active {
  background-color: #007bff;
  color: white;
  border-color: #007bff;
}

/* 订单项 */
.order-item {
  background-color: white;
  border-radius: 8px;
  overflow: hidden;
  margin-bottom: 1rem;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  background-color: #f8f9fa;
  border-bottom: 1px solid #eee;
}

.order-info p {
  margin: 0.25rem 0;
  color: #666;
}

.order-status {
  font-weight: bold;
  padding: 0.25rem 0.75rem;
  border-radius: 20px;
  font-size: 0.9rem;
}

.status-待付款 {
  background-color: #fff3cd;
  color: #856404;
}

.status-待发货 {
  background-color: #d1ecf1;
  color: #0c5460;
}

.status-已发货 {
  background-color: #d4edda;
  color: #155724;
}

.status-已完成 {
  background-color: #d1ecf1;
  color: #0c5460;
}

.status-已取消 {
  background-color: #f8d7da;
  color: #721c24;
}

/* 订单商品 */
.order-items {
  padding: 1rem;
  border-bottom: 1px solid #eee;
}

.item {
  display: flex;
  align-items: center;
  gap: 1rem;
  padding: 0.5rem 0;
  cursor: pointer;
}

.item:hover {
  background-color: #f8f9fa;
}

.item img {
  width: 60px;
  height: 60px;
  object-fit: cover;
  border-radius: 4px;
}

.item-info h4 {
  margin: 0 0 0.25rem 0;
  color: #333;
}

.item-info p {
  margin: 0;
  color: #666;
  font-size: 0.9rem;
}

/* 订单底部 */
.order-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
}

.total {
  color: #333;
}

.total span {
  color: #e74c3c;
  font-weight: bold;
}

.actions {
  display: flex;
  gap: 1rem;
}

.actions button {
  padding: 0.5rem 1rem;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 0.9rem;
}

.pay-btn {
  background-color: #e74c3c;
  color: white;
}

.cancel-btn {
  background-color: #6c757d;
  color: white;
}

.confirm-btn {
  background-color: #28a745;
  color: white;
}

.review-btn {
  background-color: #007bff;
  color: white;
}

/* 无订单提示 */
.no-orders {
  text-align: center;
  padding: 5rem 1rem;
  color: #666;
}

.no-orders button {
  margin-top: 1rem;
  padding: 0.75rem 1.5rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
}

@media (max-width: 768px) {
  .order-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 0.5rem;
  }
  
  .order-footer {
    flex-direction: column;
    gap: 1rem;
    text-align: center;
  }
  
  .actions {
    width: 100%;
    justify-content: center;
  }
  
  .actions button {
    width: 100%;
    margin: 0;
  }
}</style>