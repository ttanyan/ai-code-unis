<template>
  <div class="cart">
    <div class="container">
      <!-- 面包屑导航 -->
      <div class="breadcrumb">
        <span @click="$router.push('/')">首页</span> > 
        <span>购物车</span>
      </div>
      
      <!-- 购物车列表 -->
      <div class="cart-list" v-if="cartItems.length > 0">
        <!-- 全选和操作 -->
        <div class="cart-header">
          <label>
            <input type="checkbox" :checked="allSelected" @change="toggleSelectAll" />
            全选
          </label>
          <button @click="clearCart" v-if="hasSelectedItems">清空已选商品</button>
        </div>
        
        <!-- 商品项 -->
        <div 
          class="cart-item" 
          v-for="(item, index) in cartItems" 
          :key="index"
        >
          <div class="item-checkbox">
            <input 
              type="checkbox" 
              :checked="item.selected" 
              @change="toggleSelectItem(index)"
            />
          </div>
          
          <div class="item-info" @click="goToProductDetail(item.product.id)">
            <img :src="item.product.image" :alt="item.product.name" />
            <div class="info-text">
              <h3>{{ item.product.name }}</h3>
              <p>分类：{{ item.product.category }}</p>
            </div>
          </div>
          
          <div class="item-price">
            ¥{{ item.product.price }}
          </div>
          
          <div class="item-quantity">
            <button @click="decreaseQuantity(index)">-</button>
            <span>{{ item.quantity }}</span>
            <button @click="increaseQuantity(index)">+</button>
          </div>
          
          <div class="item-total">
            ¥{{ (item.product.price * item.quantity).toFixed(2) }}
          </div>
          
          <div class="item-actions">
            <button @click="removeItem(index)">删除</button>
          </div>
        </div>
      </div>
      
      <!-- 无商品提示 -->
      <div class="no-cart-items" v-else>
        <p>您的购物车为空</p>
        <button @click="$router.push('/products')">去购物</button>
      </div>
      
      <!-- 结算栏 -->
      <div class="checkout-bar" v-if="cartItems.length > 0">
        <div class="selected-count">
          已选择 {{ selectedCount }} 件商品
        </div>
        
        <div class="total-price">
          合计：<span>¥{{ totalPrice.toFixed(2) }}</span>
        </div>
        
        <button 
          class="checkout-btn" 
          :disabled="!hasSelectedItems"
          @click="proceedToCheckout"
        >
          去结算
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 购物车商品列表
const cartItems = ref([])

// 组件挂载时从localStorage加载购物车数据
onMounted(() => {
  loadCart()
})

// 加载购物车
const loadCart = () => {
  const savedCart = localStorage.getItem('cart')
  if (savedCart) {
    cartItems.value = JSON.parse(savedCart)
  }
}

// 保存购物车
const saveCart = () => {
  localStorage.setItem('cart', JSON.stringify(cartItems.value))
}

// 计算是否全选
const allSelected = computed(() => {
  return cartItems.value.length > 0 && cartItems.value.every(item => item.selected)
})

// 计算已选商品数量
const selectedCount = computed(() => {
  return cartItems.value.filter(item => item.selected).length
})

// 计算总价
const totalPrice = computed(() => {
  return cartItems.value
    .filter(item => item.selected)
    .reduce((total, item) => total + (item.product.price * item.quantity), 0)
})

// 是否有已选商品
const hasSelectedItems = computed(() => {
  return cartItems.value.some(item => item.selected)
})

// 切换全选状态
const toggleSelectAll = () => {
  const newSelectedState = !allSelected.value
  cartItems.value.forEach(item => {
    item.selected = newSelectedState
  })
  saveCart()
}

// 切换单个商品选择状态
const toggleSelectItem = (index) => {
  cartItems.value[index].selected = !cartItems.value[index].selected
  saveCart()
}

// 增加商品数量
const increaseQuantity = (index) => {
  cartItems.value[index].quantity++
  saveCart()
}

// 减少商品数量
const decreaseQuantity = (index) => {
  if (cartItems.value[index].quantity > 1) {
    cartItems.value[index].quantity--
    saveCart()
  }
}

// 删除商品
const removeItem = (index) => {
  if (confirm('确定要删除这个商品吗？')) {
    cartItems.value.splice(index, 1)
    saveCart()
    alert('商品已删除')
  }
}

// 清空已选商品
const clearCart = () => {
  if (confirm('确定要清空已选商品吗？')) {
    // 过滤掉未选中的商品
    cartItems.value = cartItems.value.filter(item => !item.selected)
    saveCart()
    alert('已选商品已清空')
  }
}

// 导航到商品详情
const goToProductDetail = (id) => {
  router.push(`/product/${id}`)
}

// 前往结算
const proceedToCheckout = () => {
  if (!hasSelectedItems.value) {
    alert('请先选择要购买的商品')
    return
  }
  
  // 创建订单
  const selectedItems = cartItems.value.filter(item => item.selected)
  const order = {
    id: `ORD${Date.now()}`,
    date: new Date().toLocaleString(),
    items: selectedItems,
    total: totalPrice.value,
    status: '待付款'
  }
  
  // 保存订单
  const orders = JSON.parse(localStorage.getItem('orders') || '[]')
  orders.unshift(order) // 添加到最前面
  localStorage.setItem('orders', JSON.stringify(orders))
  
  // 从购物车中移除已购买的商品
  cartItems.value = cartItems.value.filter(item => !item.selected)
  saveCart()
  
  // 跳转到订单页面
  alert(`订单创建成功！订单号：${order.id}\n\n请尽快完成支付。`)
  router.push('/orders')
}
</script>

<style>
.cart {
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

/* 购物车头部 */
.cart-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem;
  background-color: white;
  border-radius: 8px 8px 0 0;
  border: 1px solid #ddd;
  border-bottom: none;
}

.cart-header label {
  display: flex;
  align-items: center;
  gap: 0.5rem;
  font-weight: bold;
}

.cart-header button {
  padding: 0.5rem 1rem;
  background-color: #6c757d;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

/* 购物车项 */
.cart-item {
  display: flex;
  align-items: center;
  padding: 1rem;
  background-color: white;
  border: 1px solid #ddd;
  border-top: none;
}

.cart-item:first-child {
  border-top: 1px solid #ddd;
}

.cart-item:last-child {
  border-radius: 0 0 8px 8px;
}

.item-checkbox {
  width: 50px;
  text-align: center;
}

.item-checkbox input {
  transform: scale(1.2);
}

.item-info {
  flex: 1;
  display: flex;
  align-items: center;
  gap: 1rem;
  cursor: pointer;
}

.item-info img {
  width: 80px;
  height: 80px;
  object-fit: cover;
  border-radius: 4px;
}

.info-text h3 {
  margin: 0 0 0.25rem 0;
  color: #333;
}

.info-text p {
  margin: 0;
  color: #666;
  font-size: 0.9rem;
}

.item-price {
  width: 120px;
  text-align: center;
  color: #e74c3c;
  font-weight: bold;
}

.item-quantity {
  width: 150px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
}

.item-quantity button {
  width: 30px;
  height: 30px;
  border: 1px solid #ddd;
  background-color: white;
  cursor: pointer;
}

.item-quantity span {
  width: 40px;
  text-align: center;
  font-weight: bold;
}

.item-total {
  width: 120px;
  text-align: center;
  color: #e74c3c;
  font-weight: bold;
}

.item-actions {
  width: 80px;
  text-align: center;
}

.item-actions button {
  padding: 0.5rem 1rem;
  background-color: #dc3545;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

/* 无商品提示 */
.no-cart-items {
  text-align: center;
  padding: 5rem 1rem;
  color: #666;
}

.no-cart-items button {
  margin-top: 1rem;
  padding: 0.75rem 1.5rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
}

/* 结算栏 */
.checkout-bar {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1rem 2rem;
  background-color: white;
  box-shadow: 0 -2px 10px rgba(0,0,0,0.1);
  z-index: 100;
}

.selected-count {
  color: #666;
}

.total-price {
  color: #333;
}

.total-price span {
  color: #e74c3c;
  font-weight: bold;
  font-size: 1.2rem;
}

.checkout-btn {
  padding: 0.75rem 1.5rem;
  background-color: #e74c3c;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
}

.checkout-btn:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

@media (max-width: 768px) {
  .cart-header {
    flex-direction: column;
    align-items: stretch;
    gap: 1rem;
  }
  
  .cart-item {
    flex-direction: column;
    align-items: stretch;
    gap: 1rem;
  }
  
  .item-info {
    flex-direction: row;
  }
  
  .item-price,
  .item-quantity,
  .item-total,
  .item-actions {
    width: 100%;
    text-align: left;
  }
  
  .item-quantity {
    justify-content: flex-start;
  }
  
  .checkout-bar {
    flex-direction: column;
    gap: 1rem;
    text-align: center;
    padding: 1rem;
  }
  
  .selected-count,
  .total-price {
    font-size: 0.9rem;
  }
}</style>