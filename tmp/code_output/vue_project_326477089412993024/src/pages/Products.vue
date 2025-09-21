<template>
  <div class="products-page">
    <!-- 产品概览 -->
    <section class="products-hero">
      <div class="hero-content">
        <h1>我们的产品与服务</h1>
        <p>提供全面的智能化解决方案，满足不同行业和场景的需求</p>
      </div>
    </section>

    <!-- 产品分类导航 -->
    <section class="products-nav">
      <div class="nav-container">
        <button 
          v-for="(category, index) in categories" 
          :key="index"
          :class="[ 'nav-btn', { active: activeCategory === category.value } ]"
          @click="changeCategory(category.value)"
        >
          {{ category.label }}
        </button>
      </div>
    </section>

    <!-- 产品列表 -->
    <section class="products-list">
      <div class="products-container">
        <div 
          class="product-card" 
          v-for="(product, index) in filteredProducts" 
          :key="index"
          @click="openProductDetail(product)"
        >
          <div class="product-image">
            <img :src="`https://picsum.photos/seed/product${index}/400/300`" :alt="product.name" />
          </div>
          <div class="product-info">
            <span class="product-category">{{ getCategoryLabel(product.category) }}</span>
            <h3>{{ product.name }}</h3>
            <p class="product-desc">{{ product.description }}</p>
            <div class="product-features">
              <span v-for="(feature, fIndex) in product.features.slice(0, 3)" :key="fIndex">{{ feature }}</span>
            </div>
            <div class="product-footer">
              <span class="price">{{ product.price }}</span>
              <button class="btn-details">查看详情</button>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- 产品详情弹窗 -->
    <div class="modal" v-if="showModal" @click="closeModal">
      <div class="modal-content" @click="stopPropagation">
        <button class="close-btn" @click="closeModal">×</button>
        
        <div class="product-detail">
          <div class="detail-image">
            <img :src="`https://picsum.photos/seed/${selectedProduct?.id}/800/600`" :alt="selectedProduct?.name" />
          </div>
          
          <div class="detail-info">
            <span class="product-category">{{ getCategoryLabel(selectedProduct?.category) }}</span>
            <h2>{{ selectedProduct?.name }}</h2>
            
            <div class="rating">
              <span v-for="star in 5" :key="star" :class="[ 'star', { filled: star <= (selectedProduct?.rating || 0) } ]">★</span>
              <span class="rating-count">({{ selectedProduct?.reviews }}条评论)</span>
            </div>
            
            <div class="price-section">
              <span class="current-price">{{ selectedProduct?.price }}</span>
              <span class="original-price" v-if="selectedProduct?.originalPrice">{{ selectedProduct?.originalPrice }}</span>
              <span class="discount" v-if="selectedProduct?.discount">{{ selectedProduct?.discount }}折</span>
            </div>
            
            <p class="product-summary">{{ selectedProduct?.summary }}</p>
            
            <div class="detail-features">
              <h3>主要特性</h3>
              <ul>
                <li v-for="(feature, index) in selectedProduct?.features" :key="index">{{ feature }}</li>
              </ul>
            </div>
            
            <div class="detail-benefits">
              <h3>客户收益</h3>
              <ul>
                <li v-for="(benefit, index) in selectedProduct?.benefits" :key="index">{{ benefit }}</li>
              </ul>
            </div>
            
            <div class="action-buttons">
              <button class="btn-primary">立即购买</button>
              <button class="btn-secondary">免费试用</button>
              <button class="btn-contact">咨询客服</button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 解决方案 -->
    <section class="solutions-section">
      <h2>行业解决方案</h2>
      <div class="solutions-grid">
        <div class="solution-card" v-for="(solution, index) in solutions" :key="index">
          <div class="solution-icon">{{ solution.icon }}</div>
          <h3>{{ solution.title }}</h3>
          <p>{{ solution.description }}</p>
          <div class="solution-industries">
            <span v-for="(industry, iIndex) in solution.industries" :key="iIndex">{{ industry }}</span>
          </div>
          <router-link :to="solution.link" class="learn-more">了解更多 →</router-link>
        </div>
      </div>
    </section>

    <!-- 客户案例 -->
    <section class="case-studies">
      <h2>客户成功案例</h2>
      <div class="cases-carousel">
        <div class="case-item" v-for="(caseItem, index) in caseStudies" :key="index">
          <div class="case-image">
            <img :src="`https://picsum.photos/seed/case${index}/400/300`" :alt="caseItem.client" />
          </div>
          <div class="case-content">
            <div class="client-info">
              <span class="client-name">{{ caseItem.client }}</span>
              <span class="industry">{{ caseItem.industry }}</span>
            </div>
            <h3>{{ caseItem.title }}</h3>
            <p>{{ caseItem.summary }}</p>
            <div class="case-results">
              <div class="result-item" v-for="(result, rIndex) in caseItem.results" :key="rIndex">
                <span class="result-value">{{ result.value }}</span>
                <span class="result-label">{{ result.label }}</span>
              </div>
            </div>
            <button class="view-details">查看详细案例</button>
          </div>
        </div>
      </div>
    </section>

    <!-- 技术支持 -->
    <section class="support-section">
      <div class="support-content">
        <h2>技术支持与服务</h2>
        <p>我们提供全方位的技术支持和服务，确保您的系统稳定运行</p>
        
        <div class="support-cards">
          <div class="support-card" v-for="(support, index) in supportServices" :key="index">
            <div class="card-icon">{{ support.icon }}</div>
            <h3>{{ support.title }}</h3>
            <p>{{ support.description }}</p>
          </div>
        </div>
        
        <div class="support-contact">
          <p>遇到问题？立即联系我们的技术支持团队</p>
          <div class="contact-methods">
            <div class="method">
              <span class="icon">📞</span>
              <span class="text">400-123-4567</span>
            </div>
            <div class="method">
              <span class="icon">✉️</span>
              <span class="text">support@huazhi.com</span>
            </div>
            <div class="method">
              <span class="icon">💬</span>
              <span class="text">在线客服</span>
            </div>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

// 产品分类
const categories = [
  { label: '全部产品', value: 'all' },
  { label: '人工智能', value: 'ai' },
  { label: '大数据', value: 'bigdata' },
  { label: '物联网', value: 'iot' },
  { label: '云计算', value: 'cloud' }
]

// 当前选中的分类
const activeCategory = ref('all')

// 产品数据
const products = [
  {
    id: 'ai-001',
    name: '智能分析平台Pro',
    category: 'ai',
    description: '基于深度学习的智能分析系统，实现精准预测和决策支持',
    features: ['机器学习算法库', '可视化分析界面', '实时数据处理', '模型自动优化', '多源数据接入'],
    price: '¥98,000/年',
    rating: 4.8,
    reviews: 128,
    summary: '智能分析平台Pro是一款功能强大的AI分析工具，适用于金融、零售、制造等多个行业。通过先进的机器学习算法，帮助企业从海量数据中挖掘有价值的信息，实现精准预测和智能决策。',
    benefits: [
      '提升数据分析效率50%以上',
      '降低人工分析成本30%',
      '提高预测准确率达到95%',
      '支持多种数据源无缝接入'
    ],
    originalPrice: '¥128,000/年',
    discount: '7.6'
  },
  {
    id: 'ai-002',
    name: '自然语言处理引擎',
    category: 'ai',
    description: '强大的NLP引擎，支持文本分析、情感识别和智能对话',
    features: ['文本分类', '情感分析', '实体识别', '语义理解', '智能问答'],
    price: '¥68,000/年',
    rating: 4.6,
    reviews: 89,
    summary: '自然语言处理引擎采用最新的深度学习技术，能够准确理解人类语言的含义和情感。广泛应用于客服系统、舆情监控、智能助手等场景，大幅提升人机交互体验。',
    benefits: [
      '准确率高达92%以上',
      '支持多种语言处理',
      '响应时间小于100ms',
      '可定制化模型训练'
    ]
  },
  {
    id: 'bigdata-001',
    name: '大数据分析平台',
    category: 'bigdata',
    description: '高效的大数据处理框架，支持海量数据的快速分析和挖掘',
    features: ['分布式计算架构', 'PB级数据处理', '多源数据接入', '实时流处理', '数据可视化'],
    price: '¥128,000/年',
    rating: 4.7,
    reviews: 156,
    summary: '大数据分析平台为企业提供端到端的数据处理解决方案。从数据采集、存储、处理到分析展示，一站式完成。支持结构化和非结构化数据处理，帮助企业充分挖掘数据价值。',
    benefits: [
      '处理速度提升10倍',
      '支持PB级数据存储',
      '实时数据分析能力',
      '丰富的可视化组件'
    ],
    originalPrice: '¥168,000/年',
    discount: '7.6'
  },
  {
    id: 'bigdata-002',
    name: '数据仓库解决方案',
    category: 'bigdata',
    description: '企业级数据仓库，实现数据集中管理和高效查询',
    features: ['数据集成', 'ETL工具', 'OLAP分析', '数据安全', '权限管理'],
    price: '¥88,000/年',
    rating: 4.5,
    reviews: 73,
    summary: '数据仓库解决方案帮助企业整合分散在各个系统的数据，建立统一的数据视图。通过优化的数据模型和索引技术，实现快速的数据查询和分析，为决策提供可靠支持。',
    benefits: [
      '查询速度提升20倍',
      '数据一致性保障',
      '灵活的权限控制',
      '支持复杂分析需求'
    ]
  },
  {
    id: 'iot-001',
    name: '物联网设备管理平台',
    category: 'iot',
    description: '全面的物联网设备管理平台，实现设备的远程监控和智能控制',
    features: ['百万级设备接入', '设备状态监控', '远程固件升级', '安全通信协议', '设备分组管理'],
    price: '¥158,000/年',
    rating: 4.9,
    reviews: 203,
    summary: '物联网设备管理平台为大规模物联网部署提供完整的管理解决方案。支持多种通信协议，实现设备的全生命周期管理，包括设备接入、配置、监控、维护和退役。',
    benefits: [
      '设备管理效率提升60%',
      '故障预警准确率90%',
      '远程维护成本降低50%',
      '支持多种通信协议'
    ],
    originalPrice: '¥198,000/年',
    discount: '8.0'
  },
  {
    id: 'iot-002',
    name: '智能传感器套件',
    category: 'iot',
    description: '高精度传感器组合，实时采集环境和设备数据',
    features: ['温度监测', '湿度监测', '压力监测', '振动监测', '无线传输'],
    price: '¥28,000/套',
    rating: 4.4,
    reviews: 45,
    summary: '智能传感器套件包含多种高精度传感器，能够实时采集环境参数和设备运行状态。通过无线网络将数据传输到管理平台，为设备健康监测和预防性维护提供数据支持。',
    benefits: [
      '测量精度达到0.1%',
      '电池续航长达2年',
      'IP67防护等级',
      '支持远程校准'
    ]
  },
  {
    id: 'cloud-001',
    name: '云服务平台',
    category: 'cloud',
    description: '稳定可靠的云计算基础设施，提供弹性计算和存储服务',
    features: ['99.99%可用性', '按需弹性扩展', '多层次安全防护', '全球节点部署', '自动化运维'],
    price: '¥198,000/年',
    rating: 4.8,
    reviews: 189,
    summary: '云服务平台提供高性能的计算、存储和网络资源，支持按需使用和弹性扩展。采用先进的虚拟化技术，确保资源的高效利用和系统的稳定运行。',
    benefits: [
      '资源利用率提升40%',
      '系统可用性99.99%',
      '部署时间缩短80%',
      '运维成本降低60%'
    ],
    originalPrice: '¥248,000/年',
    discount: '8.0'
  },
  {
    id: 'cloud-002',
    name: '容器云平台',
    category: 'cloud',
    description: '基于Kubernetes的容器化平台，实现应用的快速部署和管理',
    features: ['容器编排', '服务发现', '自动伸缩', '持续集成', '微服务治理'],
    price: '¥128,000/年',
    rating: 4.7,
    reviews: 134,
    summary: '容器云平台基于业界标准的Kubernetes技术，为企业提供现代化的应用部署和管理平台。支持微服务架构，实现应用的快速迭代和高效运维。',
    benefits: [
      '部署效率提升70%',
      '资源隔离更安全',
      '支持DevOps流程',
      '故障恢复时间<1分钟'
    ]
  }
]

// 行业解决方案
const solutions = [
  {
    icon: '🏭',
    title: '智能制造解决方案',
    description: '通过物联网、大数据和人工智能技术，实现生产过程的全面数字化和智能化',
    industries: ['汽车制造', '电子装配', '机械加工', '食品饮料'],
    link: '/solutions/manufacturing'
  },
  {
    icon: '🏥',
    title: '智慧医疗解决方案',
    description: '利用AI和大数据技术提升诊疗效率和医疗服务质量，改善患者就医体验',
    industries: ['三甲医院', '连锁诊所', '体检中心', '医药研发'],
    link: '/solutions/healthcare'
  },
  {
    icon: '🛍️',
    title: '智慧零售解决方案',
    description: '通过数据分析和智能推荐，优化商品管理、营销策略和客户体验',
    industries: ['连锁超市', '百货商场', '电商平台', '品牌专卖店'],
    link: '/solutions/retail'
  },
  {
    icon: '🏦',
    title: '智慧金融解决方案',
    description: '运用AI技术提升风控能力、优化投资决策、改善客户服务体验',
    industries: ['银行', '保险', '证券', '基金'],
    link: '/solutions/finance'
  },
  {
    icon: '🚚',
    title: '智慧物流解决方案',
    description: '通过物联网和路径优化算法，提升物流效率，降低运输成本',
    industries: ['快递公司', '货运物流', '仓储管理', '供应链企业'],
    link: '/solutions/logistics'
  },
  {
    icon: '🏫',
    title: '智慧教育解决方案',
    description: '利用AI技术实现个性化教学，提升教学质量和学习效果',
    industries: ['高等院校', '职业培训', 'K12教育', '在线教育'],
    link: '/solutions/education'
  }
]

// 客户案例
const caseStudies = [
  {
    client: '某大型制造企业',
    industry: '制造业',
    title: '智能制造升级项目',
    summary: '通过部署华智科技的智能制造解决方案，实现了生产过程的全面数字化和智能化，大幅提升了生产效率和产品质量。',
    results: [
      { value: '35%', label: '生产效率提升' },
      { value: '50%', label: '故障率降低' },
      { value: '20%', label: '运营成本下降' }
    ]
  },
  {
    client: '某知名零售集团',
    industry: '零售业',
    title: '智慧零售解决方案',
    summary: '利用大数据分析和人工智能技术，优化了商品推荐、库存管理和营销策略，显著提升了销售额和客户满意度。',
    results: [
      { value: '28%', label: '销售额增长' },
      { value: '40%', label: '库存周转提升' },
      { value: '35%', label: '客户满意度提高' }
    ]
  },
  {
    client: '某三甲医院',
    industry: '医疗健康',
    title: '智慧医疗系统建设',
    summary: '部署了智能诊断辅助系统和患者管理系统，提高了诊疗效率和医疗服务质量，减轻了医护人员的工作负担。',
    results: [
      { value: '50%', label: '诊断效率提升' },
      { value: '30%', label: '误诊率降低' },
      { value: '45%', label: '患者等待时间缩短' }
    ]
  }
]

// 技术支持服务
const supportServices = [
  {
    icon: '📞',
    title: '7x24小时技术支持',
    description: '专业的技术支持团队随时待命，解决您在使用过程中遇到的任何问题'
  },
  {
    icon: '🔧',
    title: '现场技术支持',
    description: '对于复杂问题，我们的工程师可提供上门服务，确保问题得到及时解决'
  },
  {
    icon: '📚',
    title: '技术文档与培训',
    description: '提供完整的技术文档和定期的培训课程，帮助您更好地使用我们的产品'
  },
  {
    icon: '🔄',
    title: '系统升级与维护',
    description: '定期发布系统更新，修复已知问题，增加新功能，确保系统始终处于最佳状态'
  }
]

// 过滤后的产品列表
const filteredProducts = computed(() => {
  if (activeCategory.value === 'all') {
    return products
  }
  return products.filter(p => p.category === activeCategory.value)
})

// 获取分类标签
const getCategoryLabel = (category) => {
  const cat = categories.find(c => c.value === category)
  return cat ? cat.label : ''
}

// 切换产品分类
const changeCategory = (category) => {
  activeCategory.value = category
}

// 产品详情弹窗相关
const showModal = ref(false)
const selectedProduct = ref(null)

// 打开产品详情
const openProductDetail = (product) => {
  selectedProduct.value = product
  showModal.value = true
  document.body.style.overflow = 'hidden'
}

// 关闭产品详情
const closeModal = () => {
  showModal.value = false
  selectedProduct.value = null
  document.body.style.overflow = 'auto'
}

// 阻止事件冒泡
const stopPropagation = (e) => {
  e.stopPropagation()
}
</script>

<style scoped>
.products-page {
  overflow-x: hidden;
}

/* 产品概览 */
.products-hero {
  height: 400px;
  background: linear-gradient(135deg, #1a73e8 0%, #4285f4 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  color: white;
}

.hero-content {
  max-width: 800px;
}

.hero-content h1 {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.hero-content p {
  font-size: 1.2rem;
  opacity: 0.9;
}

/* 产品分类导航 */
.products-nav {
  padding: 2rem 1rem;
  background: white;
  box-shadow: 0 2px 10px rgba(0,0,0,0.1);
}

.nav-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: 1rem;
}

.nav-btn {
  padding: 0.6rem 1.5rem;
  border: 2px solid #1a73e8;
  background: white;
  color: #1a73e8;
  border-radius: 30px;
  cursor: pointer;
  transition: all 0.3s ease;
  font-weight: 500;
}

.nav-btn:hover {
  background: #e3f2fd;
}

.nav-btn.active {
  background: #1a73e8;
  color: white;
}

/* 产品列表 */
.products-list {
  padding: 5rem 1rem;
  background: #f8fafc;
}

.products-container {
  max-width: 1200px;
  margin: 0 auto;
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 2rem;
}

.product-card {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
  cursor: pointer;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.product-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 25px rgba(0,0,0,0.15);
}

.product-image {
  height: 200px;
  overflow: hidden;
}

.product-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.product-card:hover .product-image img {
  transform: scale(1.05);
}

.product-info {
  padding: 1.5rem;
}

.product-category {
  display: inline-block;
  padding: 0.3rem 0.8rem;
  background: #e3f2fd;
  color: #1a73e8;
  border-radius: 6px;
  font-size: 0.8rem;
  font-weight: 500;
  margin-bottom: 0.8rem;
}

.product-info h3 {
  color: #1a73e8;
  margin-bottom: 0.8rem;
  font-size: 1.3rem;
}

.product-desc {
  color: #555;
  line-height: 1.6;
  margin-bottom: 1rem;
}

.product-features {
  display: flex;
  flex-wrap: wrap;
  gap: 0.5rem;
  margin-bottom: 1.5rem;
}

.product-features span {
  padding: 0.3rem 0.6rem;
  background: #f0f4f8;
  color: #555;
  border-radius: 6px;
  font-size: 0.8rem;
}

.product-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.price {
  font-size: 1.2rem;
  font-weight: bold;
  color: #1a73e8;
}

.btn-details {
  padding: 0.6rem 1.2rem;
  background: #1a73e8;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background 0.3s ease;
}

.btn-details:hover {
  background: #1557b0;
}

/* 产品详情弹窗 */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0,0,0,0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
  animation: fadeIn 0.3s ease;
}

.modal-content {
  background: white;
  border-radius: 12px;
  width: 90%;
  max-width: 1000px;
  max-height: 90vh;
  overflow-y: auto;
  position: relative;
  animation: slideIn 0.3s ease;
}

.close-btn {
  position: absolute;
  top: 1rem;
  right: 1rem;
  font-size: 2rem;
  background: none;
  border: none;
  color: #999;
  cursor: pointer;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  transition: background 0.3s ease;
}

.close-btn:hover {
  background: #f0f4f8;
  color: #333;
}

.product-detail {
  display: flex;
  flex-wrap: wrap;
}

.detail-image {
  flex: 1;
  min-width: 300px;
}

.detail-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.detail-info {
  flex: 1;
  min-width: 300px;
  padding: 2rem;
}

.detail-info .product-category {
  margin-bottom: 0.5rem;
}

.detail-info h2 {
  color: #1a73e8;
  margin-bottom: 1rem;
  font-size: 1.8rem;
}

.rating {
  display: flex;
  align-items: center;
  margin-bottom: 1rem;
}

.star {
  color: #ddd;
  font-size: 1.2rem;
}

.star.filled {
  color: #ffc107;
}

.rating-count {
  margin-left: 0.5rem;
  color: #666;
  font-size: 0.9rem;
}

.price-section {
  margin-bottom: 1.5rem;
}

.current-price {
  font-size: 1.5rem;
  font-weight: bold;
  color: #1a73e8;
  margin-right: 1rem;
}

.original-price {
  text-decoration: line-through;
  color: #999;
  margin-right: 0.5rem;
}

.discount {
  background: #ff5722;
  color: white;
  padding: 0.2rem 0.5rem;
  border-radius: 4px;
  font-size: 0.9rem;
}

.product-summary {
  color: #555;
  line-height: 1.6;
  margin-bottom: 1.5rem;
}

.detail-features, .detail-benefits {
  margin-bottom: 1.5rem;
}

.detail-features h3, .detail-benefits h3 {
  color: #1a73e8;
  margin-bottom: 1rem;
  font-size: 1.2rem;
}

.detail-features ul, .detail-benefits ul {
  list-style: none;
  padding-left: 1.2rem;
}

.detail-features li, .detail-benefits li {
  color: #555;
  margin-bottom: 0.5rem;
  position: relative;
}

.detail-features li::before, .detail-benefits li::before {
  content: '•';
  color: #1a73e8;
  font-weight: bold;
  position: absolute;
  left: -1rem;
}

.action-buttons {
  display: flex;
  gap: 1rem;
  margin-top: 2rem;
}

.btn-primary, .btn-secondary, .btn-contact {
  padding: 0.8rem 1.5rem;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.3s ease;
  font-weight: 500;
}

.btn-primary {
  background: #1a73e8;
  color: white;
  border: none;
  flex: 1;
}

.btn-primary:hover {
  background: #1557b0;
}

.btn-secondary {
  background: white;
  color: #1a73e8;
  border: 1px solid #1a73e8;
  flex: 1;
}

.btn-secondary:hover {
  background: #e3f2fd;
}

.btn-contact {
  background: #4caf50;
  color: white;
  border: none;
  flex: 1;
}

.btn-contact:hover {
  background: #388e3c;
}

/* 行业解决方案 */
.solutions-section {
  padding: 5rem 1rem;
  text-align: center;
}

.solutions-section h2 {
  font-size: 2rem;
  color: #1a73e8;
  margin-bottom: 3rem;
}

.solutions-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
  gap: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.solution-card {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
}

.solution-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.solution-card h3 {
  color: #1a73e8;
  margin-bottom: 1rem;
  font-size: 1.3rem;
}

.solution-card p {
  color: #555;
  line-height: 1.6;
  margin-bottom: 1.5rem;
}

.solution-industries {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 0.5rem;
  margin-bottom: 1.5rem;
}

.solution-industries span {
  padding: 0.3rem 0.8rem;
  background: #f0f4f8;
  color: #555;
  border-radius: 6px;
  font-size: 0.8rem;
}

.learn-more {
  color: #1a73e8;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.3s ease;
}

.learn-more:hover {
  color: #1557b0;
}

/* 客户案例 */
.case-studies {
  padding: 5rem 1rem;
  background: #f0f4f8;
}

.case-studies h2 {
  text-align: center;
  font-size: 2rem;
  color: #1a73e8;
  margin-bottom: 3rem;
}

.cases-carousel {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
  gap: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.case-item {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
}

.case-image {
  height: 200px;
  overflow: hidden;
}

.case-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.case-item:hover .case-image img {
  transform: scale(1.05);
}

.case-content {
  padding: 1.5rem;
}

.client-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 0.8rem;
  font-size: 0.9rem;
  color: #666;
}

.client-name {
  font-weight: 500;
  color: #1a73e8;
}

.case-content h3 {
  color: #1a73e8;
  margin-bottom: 0.8rem;
  font-size: 1.2rem;
}

.case-content p {
  color: #555;
  line-height: 1.6;
  margin-bottom: 1.2rem;
}

.case-results {
  display: flex;
  justify-content: space-around;
  margin-bottom: 1.5rem;
  padding: 1rem 0;
  border-top: 1px solid #eee;
  border-bottom: 1px solid #eee;
}

.result-item {
  text-align: center;
}

.result-value {
  display: block;
  font-size: 1.3rem;
  font-weight: bold;
  color: #1a73e8;
  margin-bottom: 0.3rem;
}

.result-label {
  font-size: 0.9rem;
  color: #666;
}

.view-details {
  width: 100%;
  padding: 0.8rem;
  background: #1a73e8;
  color: white;
  border: none;
  border-radius: 6px;
  cursor: pointer;
  transition: background 0.3s ease;
}

.view-details:hover {
  background: #1557b0;
}

/* 技术支持 */
.support-section {
  padding: 5rem 1rem;
  background: linear-gradient(135deg, #1a73e8 0%, #4285f4 100%);
  color: white;
}

.support-content {
  max-width: 1200px;
  margin: 0 auto;
  text-align: center;
}

.support-content h2 {
  font-size: 2rem;
  margin-bottom: 1rem;
}

.support-content p {
  margin-bottom: 3rem;
  opacity: 0.9;
}

.support-cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
  margin-bottom: 3rem;
}

.support-card {
  background: rgba(255,255,255,0.1);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255,255,255,0.2);
  padding: 2rem;
  border-radius: 12px;
}

.card-icon {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.support-card h3 {
  margin-bottom: 1rem;
  font-size: 1.3rem;
}

.support-card p {
  opacity: 0.9;
}

.support-contact {
  background: rgba(255,255,255,0.15);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255,255,255,0.2);
  padding: 2rem;
  border-radius: 12px;
  display: inline-block;
}

.support-contact p {
  margin-bottom: 1.5rem;
}

.contact-methods {
  display: flex;
  justify-content: center;
  gap: 2rem;
}

.method {
  display: flex;
  align-items: center;
  gap: 0.5rem;
}

.method .icon {
  font-size: 1.5rem;
}

.method .text {
  font-size: 1.1rem;
  font-weight: 500;
}

/* 动画效果 */
@keyframes fadeIn {
  from { opacity: 0; }
  to { opacity: 1; }
}

@keyframes slideIn {
  from { transform: translateY(50px); opacity: 0; }
  to { transform: translateY(0); opacity: 1; }
}

/* 响应式设计 */
@media (max-width: 768px) {
  .hero-content h1 {
    font-size: 2rem;
  }

  .product-detail {
    flex-direction: column;
  }

  .detail-info {
    padding: 1.5rem;
  }

  .action-buttons {
    flex-direction: column;
  }

  .contact-methods {
    flex-direction: column;
    gap: 1rem;
  }
}
</style>