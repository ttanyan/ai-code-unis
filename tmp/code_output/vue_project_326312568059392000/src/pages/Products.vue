<template>
  <div class="products">
    <div class="container">
      <!-- 面包屑导航 -->
      <div class="breadcrumb">
        <span @click="$router.push('/')">首页</span> 
        <span v-if="currentCategory"> > {{ currentCategory }}</span>
        <span v-if="searchKeyword"> > 搜索"{{ searchKeyword }}"</span>
      </div>
      
      <!-- 筛选和排序 -->
      <div class="filters">
        <div class="category-filter">
          <button 
            :class="{ active: selectedCategory === '全部' }" 
            @click="filterByCategory('全部')"
          >
            全部
          </button>
          <button 
            v-for="category in categories" 
            :key="category" 
            :class="{ active: selectedCategory === category }" 
            @click="filterByCategory(category)"
          >
            {{ category }}
          </button>
        </div>
        
        <div class="sort-options">
          <select v-model="sortBy" @change="sortProducts">
            <option value="default">默认排序</option>
            <option value="price-asc">价格从低到高</option>
            <option value="price-desc">价格从高到低</option>
            <option value="newest">最新上架</option>
          </select>
        </div>
      </div>
      
      <!-- 商品列表 -->
      <div class="products-list" v-if="filteredAndSortedProducts.length > 0">
        <div 
          class="product-card" 
          v-for="product in filteredAndSortedProducts" 
          :key="product.id"
          @click="goToProductDetail(product.id)"
        >
          <img :src="product.image" :alt="product.name" />
          <h3>{{ product.name }}</h3>
          <p class="category">{{ product.category }}</p>
          <p class="price">¥{{ product.price }}</p>
          <button @click.stop="addToCart(product)">加入购物车</button>
        </div>
      </div>
      
      <!-- 无商品提示 -->
      <div class="no-products" v-else>
        <p>暂无符合条件的商品</p>
        <button @click="resetFilters">重置筛选条件</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

// 所有商品数据
const products = [
  {
    id: 1,
    name: 'Apple iPhone 14 Pro',
    price: 7999,
    category: '手机',
    image: 'https://picsum.photos/seed/iphone/300/300'
  },
  {
    id: 2,
    name: 'Samsung Galaxy S23',
    price: 6999,
    category: '手机',
    image: 'https://picsum.photos/seed/samsung/300/300'
  },
  {
    id: 3,
    name: 'Sony WH-1000XM5',
    price: 2299,
    category: '耳机',
    image: 'https://picsum.photos/seed/sonyheadphone/300/300'
  },
  {
    id: 4,
    name: 'Dyson V11 吸尘器',
    price: 4999,
    category: '家电',
    image: 'https://picsum.photos/seed/dyson/300/300'
  },
  {
    id: 5,
    name: 'Nike Air Max 270',
    price: 899,
    category: '鞋类',
    image: 'https://picsum.photos/seed/nike/300/300'
  },
  {
    id: 6,
    name: 'Adidas Ultraboost',
    price: 1299,
    category: '鞋类',
    image: 'https://picsum.photos/seed/adidas/300/300'
  },
  {
    id: 7,
    name: 'MacBook Pro M2',
    price: 12999,
    category: '电脑',
    image: 'https://picsum.photos/seed/macbook/300/300'
  },
  {
    id: 8,
    name: 'iPad Air',
    price: 4799,
    category: '平板',
    image: 'https://picsum.photos/seed/ipad/300/300'
  },
  {
    id: 9,
    name: 'Bose QuietComfort 45',
    price: 2299,
    category: '耳机',
    image: 'https://picsum.photos/seed/bose/300/300'
  },
  {
    id: 10,
    name: 'GoPro HERO11',
    price: 3499,
    category: '相机',
    image: 'https://picsum.photos/seed/gopro/300/300'
  }
]

// 分类列表
const categories = ['手机', '电脑', '平板', '耳机', '家电', '鞋类', '相机']

// 当前选中的分类
const selectedCategory = ref('全部')

// 排序方式
const sortBy = ref('default')

// 搜索关键词
const searchKeyword = ref('')

// 当前分类（用于面包屑）
const currentCategory = computed(() => {
  return route.query.category || ''
})

// 过滤后的商品
const filteredAndSortedProducts = computed(() => {
  let result = [...products]
  
  // 根据路由参数过滤分类
  if (route.query.category) {
    result = result.filter(p => p.category === route.query.category)
  }
  
  // 根据搜索关键词过滤
  if (route.query.keyword) {
    const keyword = route.query.keyword.toLowerCase()
    result = result.filter(p => 
      p.name.toLowerCase().includes(keyword) || 
      p.category.toLowerCase().includes(keyword)
    )
  }
  
  // 根据选中的分类过滤
  if (selectedCategory.value !== '全部') {
    result = result.filter(p => p.category === selectedCategory.value)
  }
  
  // 排序
  switch (sortBy.value) {
    case 'price-asc':
      result.sort((a, b) => a.price - b.price)
      break
    case 'price-desc':
      result.sort((a, b) => b.price - a.price)
      break
    case 'newest':
      // 模拟按上架时间排序（这里用id代替）
      result.sort((a, b) => b.id - a.id)
      break
    default:
      // 默认排序保持原顺序
      break
  }
  
  return result
})

// 组件挂载时根据路由参数设置初始状态
onMounted(() => {
  // 设置搜索关键词
  if (route.query.keyword) {
    searchKeyword.value = route.query.keyword
  }
  
  // 设置分类筛选
  if (route.query.category) {
    selectedCategory.value = route.query.category
  }
})

// 按分类筛选
const filterByCategory = (category) => {
  selectedCategory.value = category
}

// 排序商品
const sortProducts = () => {
  // 排序已经在computed中处理
}

// 导航到商品详情
const goToProductDetail = (id) => {
  router.push(`/product/${id}`)
}

// 添加到购物车
const addToCart = (product) => {
  const cart = JSON.parse(localStorage.getItem('cart') || '[]')
  const existingItem = cart.find(item => item.product.id === product.id)
  
  if (existingItem) {
    existingItem.quantity += 1
  } else {
    cart.push({
      product: product,
      quantity: 1,
      selected: true
    })
  }
  
  localStorage.setItem('cart', JSON.stringify(cart))
  alert(`${product.name} 已加入购物车！`)
}

// 重置筛选条件
const resetFilters = () => {
  selectedCategory.value = '全部'
  sortBy.value = 'default'
  
  // 如果有路由参数，重置路由
  if (route.query.category || route.query.keyword) {
    router.push('/products')
  }
}
</script>

<style>
.products {
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

/* 筛选和排序 */
.filters {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
  flex-wrap: wrap;
  gap: 1rem;
}

.category-filter {
  display: flex;
  gap: 1rem;
  flex-wrap: wrap;
}

.category-filter button {
  padding: 0.5rem 1rem;
  border: 1px solid #ddd;
  background-color: white;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.category-filter button.active {
  background-color: #007bff;
  color: white;
  border-color: #007bff;
}

.sort-options select {
  padding: 0.5rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  font-size: 1rem;
}

/* 商品列表 */
.products-list {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
  gap: 2rem;
}

.product-card {
  background-color: white;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
  cursor: pointer;
  transition: transform 0.3s ease;
}

.product-card:hover {
  transform: translateY(-5px);
}

.product-card img {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.product-card h3 {
  padding: 1rem;
  margin: 0;
  color: #333;
}

.product-card .category {
  padding: 0 1rem;
  color: #666;
  font-size: 0.9rem;
  margin: 0;
}

.product-card .price {
  padding: 0 1rem;
  color: #e74c3c;
  font-weight: bold;
  margin: 0.5rem 0;
}

.product-card button {
  width: 100%;
  padding: 0.75rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-top: 1px solid #eee;
  font-size: 1rem;
  cursor: pointer;
}

/* 无商品提示 */
.no-products {
  text-align: center;
  padding: 5rem 1rem;
  color: #666;
}

.no-products button {
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
  .filters {
    flex-direction: column;
    align-items: stretch;
  }
  
  .sort-options {
    order: -1;
    margin-bottom: 1rem;
  }
}</style>