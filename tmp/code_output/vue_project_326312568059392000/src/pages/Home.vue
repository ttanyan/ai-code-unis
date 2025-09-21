<template>
  <div class="home">
    <!-- 轮播图 -->
    <div class="carousel">
      <div class="carousel-item" v-for="(item, index) in carouselItems" :key="index" :class="{ active: currentIndex === index }">
        <img :src="item.image" :alt="item.title" />
        <div class="carousel-content">
          <h2>{{ item.title }}</h2>
          <p>{{ item.description }}</p>
          <button @click="goToProducts(item.category)">{{ item.buttonText }}</button>
        </div>
      </div>
      
      <!-- 轮播指示器 -->
      <div class="carousel-indicators">
        <span 
          v-for="(item, index) in carouselItems" 
          :key="index" 
          :class="{ active: currentIndex === index }"
          @click="setCurrentIndex(index)"
        ></span>
      </div>
      
      <!-- 轮播控制按钮 -->
      <button class="carousel-prev" @click="prevSlide">‹</button>
      <button class="carousel-next" @click="nextSlide">›</button>
    </div>
    
    <!-- 热门商品分类 -->
    <div class="categories">
      <div class="container">
        <h2>热门商品分类</h2>
        <div class="category-list">
          <div 
            class="category-item" 
            v-for="category in categories" 
            :key="category.name"
            @click="goToCategory(category.name)"
          >
            <img :src="category.image" :alt="category.name" />
            <h3>{{ category.name }}</h3>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 推荐商品 -->
    <div class="recommendations">
      <div class="container">
        <h2>为您推荐</h2>
        <div class="products-list">
          <div 
            class="product-card" 
            v-for="product in recommendedProducts" 
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
      </div>
    </div>
    
    <!-- 促销活动 -->
    <div class="promotions">
      <div class="container">
        <h2>限时促销</h2>
        <div class="promotion-list">
          <div class="promotion-item" v-for="promo in promotions" :key="promo.id">
            <img :src="promo.image" :alt="promo.title" />
            <div class="promotion-content">
              <h3>{{ promo.title }}</h3>
              <p>{{ promo.description }}</p>
              <div class="countdown">
                剩余时间：{{ promo.days }}天 {{ promo.hours }}小时 {{ promo.minutes }}分
              </div>
              <button @click="goToProducts(promo.category)">立即抢购</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    
    <!-- 底部信息 -->
    <div class="info">
      <div class="container">
        <div class="info-content">
          <div class="info-item">
            <h3>正品保障</h3>
            <p>所有商品均为正品，假一赔十</p>
          </div>
          <div class="info-item">
            <h3>7天无理由退货</h3>
            <p>购物无忧，满意再收货</p>
          </div>
          <div class="info-item">
            <h3>满99包邮</h3>
            <p>全场满99元免运费</p>
          </div>
          <div class="info-item">
            <h3>24小时客服</h3>
            <p>随时为您解决购物问题</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 轮播图数据
const carouselItems = [
  {
    image: 'https://picsum.photos/seed/carousel1/1200/400',
    title: '新品上市',
    description: '最新款iPhone 14 Pro，搭载A16芯片，性能强劲，拍照出色',
    buttonText: '立即购买',
    category: '手机'
  },
  {
    image: 'https://picsum.photos/seed/carousel2/1200/400',
    title: '限时优惠',
    description: 'MacBook Pro M2笔记本电脑，现在购买立减1000元',
    buttonText: '立即抢购',
    category: '电脑'
  },
  {
    image: 'https://picsum.photos/seed/carousel3/1200/400',
    title: '品质生活',
    description: 'Sony WH-1000XM5降噪耳机，音质出众，佩戴舒适',
    buttonText: '立即体验',
    category: '耳机'
  }
]

// 当前轮播图索引
const currentIndex = ref(0)

// 自动轮播定时器
let intervalId = null

// 设置当前索引
const setCurrentIndex = (index) => {
  currentIndex.value = index
}

// 上一张
const prevSlide = () => {
  currentIndex.value = (currentIndex.value - 1 + carouselItems.length) % carouselItems.length
}

// 下一张
const nextSlide = () => {
  currentIndex.value = (currentIndex.value + 1) % carouselItems.length
}

// 启动自动轮播
const startAutoPlay = () => {
  intervalId = setInterval(() => {
    nextSlide()
  }, 5000) // 每5秒切换一次
}

// 停止自动轮播
const stopAutoPlay = () => {
  if (intervalId) {
    clearInterval(intervalId)
    intervalId = null
  }
}

// 导航到商品列表
const goToProducts = (category) => {
  router.push({
    path: '/products',
    query: { category: category }
  })
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

// 所有商品数据（用于推荐）
const allProducts = [
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

// 热门分类
const categories = [
  { name: '手机', image: 'https://picsum.photos/seed/phone/300/200' },
  { name: '电脑', image: 'https://picsum.photos/seed/computer/300/200' },
  { name: '耳机', image: 'https://picsum.photos/seed/headphone/300/200' },
  { name: '家电', image: 'https://picsum.photos/seed/appliance/300/200' },
  { name: '鞋类', image: 'https://picsum.photos/seed/shoes/300/200' },
  { name: '相机', image: 'https://picsum.photos/seed/camera/300/200' }
]

// 推荐商品（随机选择4个）
const recommendedProducts = ref([])

// 生成推荐商品
const generateRecommendations = () => {
  // 随机打乱商品数组
  const shuffled = [...allProducts].sort(() => 0.5 - Math.random())
  // 取前4个作为推荐
  recommendedProducts.value = shuffled.slice(0, 4)
}

// 促销活动
const promotions = [
  {
    id: 1,
    title: '双11大促',
    description: '全场商品低至5折，更有爆款直降千元，错过再等一年！',
    image: 'https://picsum.photos/seed/promo1/600/300',
    category: '全部',
    days: 3,
    hours: 12,
    minutes: 30
  },
  {
    id: 2,
    title: '新品首发',
    description: '最新款智能手表首发特惠，前100名下单送精美表带！',
    image: 'https://picsum.photos/seed/promo2/600/300',
    category: '智能设备',
    days: 1,
    hours: 5,
    minutes: 45
  }
]

// 更新倒计时
const updateCountdown = () => {
  promotions.forEach(promo => {
    // 模拟倒计时递减
    promo.minutes--
    if (promo.minutes < 0) {
      promo.minutes = 59
      promo.hours--
      if (promo.hours < 0) {
        promo.hours = 23
        promo.days--
        if (promo.days < 0) {
          promo.days = 3 // 重置为3天
        }
      }
    }
  })
}

// 定时更新倒计时
let countdownInterval = null

// 导航到分类
const goToCategory = (categoryName) => {
  router.push({
    path: '/products',
    query: { category: categoryName }
  })
}

// 组件挂载时的初始化
onMounted(() => {
  // 生成推荐商品
  generateRecommendations()
  
  // 启动轮播自动播放
  startAutoPlay()
  
  // 启动倒计时
  countdownInterval = setInterval(updateCountdown, 60000) // 每分钟更新一次
})

// 组件卸载前的清理
onUnmounted(() => {
  // 清除轮播定时器
  stopAutoPlay()
  
  // 清除倒计时定时器
  if (countdownInterval) {
    clearInterval(countdownInterval)
    countdownInterval = null
  }
})
</script>

<style>
.home {
  min-height: 100vh;
}

/* 轮播图 */
.carousel {
  position: relative;
  height: 400px;
  overflow: hidden;
}

.carousel-item {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  opacity: 0;
  transition: opacity 0.5s ease;
}

.carousel-item.active {
  opacity: 1;
}

.carousel-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.carousel-content {
  position: absolute;
  top: 50%;
  left: 10%;
  transform: translateY(-50%);
  color: white;
  text-shadow: 0 2px 4px rgba(0,0,0,0.5);
}

.carousel-content h2 {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.carousel-content p {
  font-size: 1.2rem;
  margin-bottom: 1.5rem;
}

.carousel-content button {
  padding: 0.75rem 1.5rem;
  background-color: #e74c3c;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
}

/* 轮播指示器 */
.carousel-indicators {
  position: absolute;
  bottom: 20px;
  left: 50%;
  transform: translateX(-50%);
  display: flex;
  gap: 10px;
}

.carousel-indicators span {
  width: 12px;
  height: 12px;
  background-color: rgba(255,255,255,0.5);
  border-radius: 50%;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

.carousel-indicators span.active {
  background-color: white;
}

/* 轮播控制按钮 */
.carousel-prev,
.carousel-next {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 50px;
  height: 50px;
  background-color: rgba(0,0,0,0.5);
  color: white;
  border: none;
  border-radius: 50%;
  font-size: 1.5rem;
  cursor: pointer;
  z-index: 10;
  transition: background-color 0.3s ease;
}

.carousel-prev {
  left: 20px;
}

.carousel-next {
  right: 20px;
}

.carousel-prev:hover,
.carousel-next:hover {
  background-color: rgba(0,0,0,0.8);
}

/* 热门商品分类 */
.categories {
  padding: 3rem 0;
  background-color: #f8f9fa;
}

.categories .container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}

.categories h2 {
  text-align: center;
  margin-bottom: 2rem;
  color: #333;
}

.category-list {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(180px, 1fr));
  gap: 2rem;
}

.category-item {
  text-align: center;
  cursor: pointer;
  transition: transform 0.3s ease;
}

.category-item:hover {
  transform: translateY(-5px);
}

.category-item img {
  width: 100%;
  height: 150px;
  object-fit: cover;
  border-radius: 8px;
  margin-bottom: 1rem;
}

.category-item h3 {
  margin: 0;
  color: #333;
}

/* 推荐商品 */
.recommendations {
  padding: 3rem 0;
}

.recommendations h2 {
  text-align: center;
  margin-bottom: 2rem;
  color: #333;
}

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

/* 促销活动 */
.promotions {
  padding: 3rem 0;
  background-color: #f8f9fa;
}

.promotions h2 {
  text-align: center;
  margin-bottom: 2rem;
  color: #333;
}

.promotion-list {
  display: grid;
  grid-template-columns: 1fr;
  gap: 2rem;
}

.promotion-item {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 2rem;
  background-color: white;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.promotion-item img {
  width: 100%;
  height: 300px;
  object-fit: cover;
}

.promotion-content {
  padding: 2rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.promotion-content h3 {
  margin: 0 0 1rem 0;
  color: #333;
}

.promotion-content p {
  margin: 0 0 1.5rem 0;
  color: #666;
  line-height: 1.6;
}

.countdown {
  margin: 0 0 1.5rem 0;
  color: #e74c3c;
  font-weight: bold;
}

.promotion-content button {
  align-self: flex-start;
  padding: 0.75rem 1.5rem;
  background-color: #e74c3c;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
}

/* 底部信息 */
.info {
  padding: 3rem 0;
  background-color: #333;
  color: white;
}

.info-content {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 2rem;
  text-align: center;
}

.info-item h3 {
  margin-bottom: 1rem;
}

.info-item p {
  color: #ccc;
}

@media (max-width: 768px) {
  .carousel-content {
    left: 5%;
    width: 90%;
  }
  
  .carousel-content h2 {
    font-size: 2rem;
  }
  
  .carousel-content p {
    font-size: 1rem;
  }
  
  .promotion-item {
    grid-template-columns: 1fr;
  }
  
  .promotion-item img {
    height: 200px;
  }
}</style>