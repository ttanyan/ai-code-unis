<template>
  <div class="product-detail">
    <div class="container">
      <!-- 面包屑导航 -->
      <div class="breadcrumb">
        <span @click="$router.push('/')">首页</span> > 
        <span @click="$router.push('/products')">商品列表</span> > 
        <span>{{ product?.category }}</span>
      </div>
      
      <!-- 商品详情 -->
      <div class="product-content" v-if="product">
        <div class="product-images">
          <img :src="product.image" :alt="product.name" />
        </div>
        
        <div class="product-info">
          <h1>{{ product.name }}</h1>
          <p class="category">分类：{{ product.category }}</p>
          <p class="price">¥{{ product.price }}</p>
          
          <div class="quantity">
            <label>数量：</label>
            <div class="quantity-control">
              <button @click="decreaseQuantity">-</button>
              <span>{{ quantity }}</span>
              <button @click="increaseQuantity">+</button>
            </div>
          </div>
          
          <div class="actions">
            <button class="add-to-cart" @click="addToCart">加入购物车</button>
            <button class="buy-now" @click="buyNow">立即购买</button>
          </div>
          
          <div class="description">
            <h3>商品详情</h3>
            <p>{{ product.description }}</p>
          </div>
        </div>
      </div>
      
      <!-- 加载中 -->
      <div class="loading" v-else>
        <p>加载中...</p>
      </div>
      
      <!-- 相关推荐 -->
      <div class="recommendations" v-if="product">
        <h2>相关推荐</h2>
        <div class="products-list">
          <div 
            class="product-card" 
            v-for="item in relatedProducts" 
            :key="item.id"
            @click="goToProductDetail(item.id)"
          >
            <img :src="item.image" :alt="item.name" />
            <h3>{{ item.name }}</h3>
            <p class="category">{{ item.category }}</p>
            <p class="price">¥{{ item.price }}</p>
            <button @click.stop="addToCartFromRecommendation(item)">加入购物车</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'

const router = useRouter()
const route = useRoute()

// 当前商品
const product = ref(null)

// 数量
const quantity = ref(1)

// 所有商品数据
const allProducts = [
  {
    id: 1,
    name: 'Apple iPhone 14 Pro',
    price: 7999,
    category: '手机',
    image: 'https://picsum.photos/seed/iphone/300/300',
    description: '最新款iPhone 14 Pro，搭载A16芯片，性能强劲，拍照出色。支持5G网络，拥有超视网膜XDR显示屏，电池续航能力大幅提升。' +
                 'Pro级摄像头系统，包括4800万像素主摄、1200万像素超广角和1200万像素长焦镜头，支持夜间模式人像和运动模式视频拍摄。' +
                 'iOS 16操作系统，带来全新的锁屏自定义功能和更强大的隐私保护。'
  },
  {
    id: 2,
    name: 'Samsung Galaxy S23',
    price: 6999,
    category: '手机',
    image: 'https://picsum.photos/seed/samsung/300/300',
    description: '三星Galaxy S23旗舰手机，搭载骁龙8 Gen 2处理器，性能卓越。6.1英寸Dynamic AMOLED 2X显示屏，支持120Hz刷新率。' +
                 '后置三摄系统：5000万像素主摄、1200万像素超广角和1000万像素长焦镜头，支持3倍光学变焦。' +
                 '4000mAh电池，支持25W有线快充和15W无线充电。IP68防水防尘等级，提供出色的耐用性。'
  },
  {
    id: 3,
    name: 'Sony WH-1000XM5',
    price: 2299,
    category: '耳机',
    image: 'https://picsum.photos/seed/sonyheadphone/300/300',
    description: '索尼WH-1000XM5降噪耳机，采用全新集成处理器V1和双噪声传感器技术，提供行业领先的主动降噪效果。' +
                 '30小时超长续航，支持快速充电（充电3分钟可播放3小时）。支持LDAC高解析度音频传输，带来极致音质体验。' +
                 '智能免摘对话功能，说话时音乐自动暂停。轻量化设计，佩戴舒适，适合长时间使用。支持多点连接，可在两个设备间无缝切换。'
  },
  {
    id: 4,
    name: 'Dyson V11 吸尘器',
    price: 4999,
    category: '家电',
    image: 'https://picsum.photos/seed/dyson/300/300',
    description: '戴森V11无绳吸尘器，配备最新的数码马达，吸力高达185AW。LCD屏幕实时显示剩余运行时间、堵塞提醒和清洁模式。' +
                 '配备多种吸头，适用于地板、地毯、缝隙、床褥等多种场景。60分钟续航时间，满足大户型清洁需求。' +
                 '整机HEPA过滤系统，可捕获99.97%小至0.3微米的颗粒物，排出洁净空气。一键倒空集尘筒，保持双手清洁。'
  },
  {
    id: 5,
    name: 'Nike Air Max 270',
    price: 899,
    category: '鞋类',
    image: 'https://picsum.photos/seed/nike/300/300',
    description: '耐克Air Max 270运动鞋，标志性270度全掌可视气垫，提供卓越缓震性能。工程网眼鞋面，透气舒适，贴合脚型。' +
                 '橡胶外底纹路设计，提供出色的抓地力和耐磨性。适合日常穿着、健身训练和休闲活动。多种配色可选，满足不同风格需求。'
  },
  {
    id: 6,
    name: 'Adidas Ultraboost',
    price: 1299,
    category: '鞋类',
    image: 'https://picsum.photos/seed/adidas/300/300',
    description: '阿迪达斯Ultraboost跑鞋，采用Primeknit编织鞋面，轻盈透气，提供袜子般的贴合感。' +
                 '全掌Boost中底，提供卓越的能量回馈和缓震性能。Continental马牌橡胶外底，湿滑路面也能提供出色抓地力。' +
                 'Torsion System抗扭转系统，增强鞋子的稳定性。适合长距离跑步和日常穿着，被誉为“踩屎感”跑鞋。'
  },
  {
    id: 7,
    name: 'MacBook Pro M2',
    price: 12999,
    category: '电脑',
    image: 'https://picsum.photos/seed/macbook/300/300',
    description: '苹果MacBook Pro M2笔记本电脑，搭载M2芯片，8核CPU和10核GPU，性能较上一代提升显著。' +
                 '14英寸Liquid视网膜XDR显示屏，支持ProMotion自适应刷新率技术，最高可达120Hz。' +
                 '配备MagSafe充电端口、HDMI端口、SDXC卡槽和三个雷雳4端口，扩展性强。24GB统一内存，1TB固态硬盘。' +
                 '长达20小时的电池续航，满足全天候使用需求。macOS Ventura操作系统，提供流畅的用户体验。'
  },
  {
    id: 8,
    name: 'iPad Air',
    price: 4799,
    category: '平板',
    image: 'https://picsum.photos/seed/ipad/300/300',
    description: '苹果iPad Air平板电脑，搭载M1芯片，性能强大，可轻松处理各种任务。10.9英寸Liquid视网膜显示屏，色彩精准，亮度高。' +
                 '支持第二代Apple Pencil和妙控键盘，提升创作和办公效率。前置1200万像素超广角摄像头，支持人物居中功能。' +
                 '后置1200万像素广角摄像头，可拍摄4K视频。支持5G网络，随时随地保持连接。' +
                 '轻薄设计，重量仅约461克，便于携带。适合学习、工作和娱乐等多种场景。'
  },
  {
    id: 9,
    name: 'Bose QuietComfort 45',
    price: 2299,
    category: '耳机',
    image: 'https://picsum.photos/seed/bose/300/300',
    description: 'Bose QuietComfort 45降噪耳机，采用先进的主动降噪技术，有效隔绝外界噪音。' +
                 '特有的Aware Mode通透模式，让您在需要时能清晰听到周围环境声音。' +
                 '40小时电池续航，支持快速充电（充电15分钟可播放3小时）。' +
                 '柔软的耳罩和头梁，提供全天候佩戴的舒适感。支持蓝牙5.1，连接稳定。' +
                 'Bose Music应用程序，方便管理设置和更新固件。'
  },
  {
    id: 10,
    name: 'GoPro HERO11',
    price: 3499,
    category: '相机',
    image: 'https://picsum.photos/seed/gopro/300/300',
    description: 'GoPro HERO11运动相机，支持5.3K60和4K120视频拍摄，画质清晰流畅。' +
                 'HyperSmooth 5.0防抖技术，在剧烈运动中也能拍摄稳定画面。' +
                 '前后双显示屏，方便自拍和构图。27米防水，无需外壳即可水下拍摄。' +
                 '支持垂直视频拍摄，适合社交媒体分享。360度水平锁定功能，确保画面始终水平。' +
                 '内置GPS，可记录轨迹信息。适合极限运动、旅行记录和内容创作。'
  }
]

// 相关推荐商品
const relatedProducts = computed(() => {
  if (!product.value) return []
  
  // 获取同分类的商品，排除当前商品
  return allProducts.filter(p => 
    p.category === product.value.category && p.id !== product.value.id
  ).slice(0, 4) // 取前4个
})

// 组件挂载时根据路由参数加载商品
onMounted(() => {
  loadProduct()
})

// 加载商品
const loadProduct = () => {
  const id = parseInt(route.params.id)
  const foundProduct = allProducts.find(p => p.id === id)
  
  if (foundProduct) {
    product.value = foundProduct
  } else {
    alert('商品不存在')
    router.push('/products')
  }
}

// 增加数量
const increaseQuantity = () => {
  quantity.value++
}

// 减少数量
const decreaseQuantity = () => {
  if (quantity.value > 1) {
    quantity.value--
  }
}

// 添加到购物车
const addToCart = () => {
  const cart = JSON.parse(localStorage.getItem('cart') || '[]')
  const existingItem = cart.find(item => item.product.id === product.value.id)
  
  if (existingItem) {
    existingItem.quantity += quantity.value
  } else {
    cart.push({
      product: product.value,
      quantity: quantity.value,
      selected: true
    })
  }
  
  localStorage.setItem('cart', JSON.stringify(cart))
  alert(`${product.value.name} 已加入购物车！`)
}

// 立即购买
const buyNow = () => {
  // 添加到购物车
  const cart = JSON.parse(localStorage.getItem('cart') || '[]')
  const existingItem = cart.find(item => item.product.id === product.value.id)
  
  if (existingItem) {
    existingItem.quantity += quantity.value
  } else {
    cart.push({
      product: product.value,
      quantity: quantity.value,
      selected: true
    })
  }
  
  localStorage.setItem('cart', JSON.stringify(cart))
  
  // 跳转到购物车
  router.push('/cart')
}

// 导航到商品详情
const goToProductDetail = (id) => {
  router.push(`/product/${id}`)
}

// 从推荐中添加到购物车
const addToCartFromRecommendation = (item) => {
  const cart = JSON.parse(localStorage.getItem('cart') || '[]')
  const existingItem = cart.find(i => i.product.id === item.id)
  
  if (existingItem) {
    existingItem.quantity += 1
  } else {
    cart.push({
      product: item,
      quantity: 1,
      selected: true
    })
  }
  
  localStorage.setItem('cart', JSON.stringify(cart))
  alert(`${item.name} 已加入购物车！`)
}
</script>

<style>
.product-detail {
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

/* 商品详情 */
.product-content {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 3rem;
  margin-bottom: 3rem;
}

.product-images img {
  width: 100%;
  height: 400px;
  object-fit: cover;
  border-radius: 8px;
}

.product-info h1 {
  margin: 0 0 1rem 0;
  color: #333;
}

.product-info .category {
  color: #666;
  margin: 0 0 0.5rem 0;
}

.product-info .price {
  color: #e74c3c;
  font-size: 1.5rem;
  font-weight: bold;
  margin: 0 0 1.5rem 0;
}

.quantity {
  margin-bottom: 1.5rem;
}

.quantity label {
  display: block;
  margin-bottom: 0.5rem;
  color: #333;
  font-weight: bold;
}

.quantity-control {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.quantity-control button {
  width: 40px;
  height: 40px;
  border: 1px solid #ddd;
  background-color: white;
  cursor: pointer;
}

.quantity-control span {
  width: 60px;
  text-align: center;
  font-size: 1.2rem;
}

.actions {
  display: flex;
  gap: 1rem;
  margin-bottom: 2rem;
}

.add-to-cart {
  flex: 1;
  padding: 0.75rem;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
}

.buy-now {
  flex: 1;
  padding: 0.75rem;
  background-color: #e74c3c;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 1rem;
  cursor: pointer;
}

.description {
  margin-top: 2rem;
}

.description h3 {
  margin: 0 0 1rem 0;
  color: #333;
}

.description p {
  color: #666;
  line-height: 1.6;
}

/* 加载中 */
.loading {
  text-align: center;
  padding: 5rem 1rem;
  color: #666;
}

/* 相关推荐 */
.recommendations {
  margin-top: 3rem;
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

@media (max-width: 768px) {
  .product-content {
    grid-template-columns: 1fr;
  }
  
  .actions {
    flex-direction: column;
  }
  
  .add-to-cart,
  .buy-now {
    width: 100%;
  }
}</style>