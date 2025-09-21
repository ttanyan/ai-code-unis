<template>
  <div class="home-page">
    <!-- 首屏大图 -->
    <section class="hero-section">
      <div class="hero-content">
        <h1>引领智能科技，创造美好未来</h1>
        <p>华智科技提供人工智能、大数据、物联网和云计算等前沿技术解决方案，助力企业数字化转型</p>
        <div class="hero-buttons">
          <router-link to="/products" class="btn-primary">了解产品</router-link>
          <router-link to="/contact" class="btn-secondary">联系我们</router-link>
        </div>
      </div>
      <div class="hero-image">
        <img src="https://picsum.photos/800/600" alt="智能科技" />
      </div>
    </section>

    <!-- 核心优势 -->
    <section class="advantages-section">
      <h2>我们的核心优势</h2>
      <div class="advantages-grid">
        <div class="advantage-card" v-for="(advantage, index) in advantages" :key="index">
          <div class="icon">{{ advantage.icon }}</div>
          <h3>{{ advantage.title }}</h3>
          <p>{{ advantage.description }}</p>
        </div>
      </div>
    </section>

    <!-- 主要产品 -->
    <section class="products-section">
      <h2>主要产品与服务</h2>
      <div class="products-grid">
        <div class="product-card" v-for="(product, index) in products" :key="index">
          <div class="product-icon">{{ product.icon }}</div>
          <h3>{{ product.name }}</h3>
          <p>{{ product.description }}</p>
          <ul class="features-list">
            <li v-for="(feature, fIndex) in product.features" :key="fIndex">{{ feature }}</li>
          </ul>
          <router-link :to="product.link" class="learn-more">了解更多 →</router-link>
        </div>
      </div>
    </section>

    <!-- 客户案例 -->
    <section class="cases-section">
      <h2>客户成功案例</h2>
      <div class="cases-carousel">
        <div class="case-card" v-for="(caseItem, index) in cases" :key="index">
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
            <div class="case-stats">
              <div class="stat-item">
                <span class="stat-value">{{ caseItem.results[0].value }}</span>
                <span class="stat-label">{{ caseItem.results[0].label }}</span>
              </div>
              <div class="stat-item">
                <span class="stat-value">{{ caseItem.results[1].value }}</span>
                <span class="stat-label">{{ caseItem.results[1].label }}</span>
              </div>
              <div class="stat-item">
                <span class="stat-value">{{ caseItem.results[2].value }}</span>
                <span class="stat-label">{{ caseItem.results[2].label }}</span>
              </div>
            </div>
            <router-link :to="caseItem.link" class="view-case">查看案例详情</router-link>
          </div>
        </div>
      </div>
    </section>

    <!-- 技术实力 -->
    <section class="tech-section">
      <h2>技术实力与创新</h2>
      <div class="tech-content">
        <div class="tech-text">
          <h3>持续创新的技术研发能力</h3>
          <p>华智科技拥有一支由博士、硕士组成的专业研发团队，在人工智能、大数据分析、物联网通信等领域拥有深厚的技术积累。我们每年将营业收入的15%以上投入研发，确保技术领先优势。</p>
          <p>公司已获得50+项技术专利，参与制定3项行业标准，在国际顶级学术会议发表论文20余篇。我们的技术创新能力得到了业界广泛认可。</p>
          <div class="tech-stats">
            <div class="stat">
              <span class="number">50+</span>
              <span class="label">技术专利</span>
            </div>
            <div class="stat">
              <span class="number">15%</span>
              <span class="label">研发投入占比</span>
            </div>
            <div class="stat">
              <span class="number">20+</span>
              <span class="label">国际论文发表</span>
            </div>
          </div>
        </div>
        <div class="tech-image">
          <img src="https://picsum.photos/600/400" alt="技术研发" />
        </div>
      </div>
    </section>

    <!-- 合作伙伴 -->
    <section class="partners-section">
      <h2>我们的合作伙伴</h2>
      <div class="partners-grid">
        <div class="partner-logo" v-for="(partner, index) in partners" :key="index">
          <img :src="`https://picsum.photos/seed/partner${index}/200/100`" :alt="partner" />
        </div>
      </div>
    </section>

    <!-- 行动号召 -->
    <section class="cta-section">
      <div class="cta-content">
        <h2>准备好开启智能化转型了吗？</h2>
        <p>立即联系我们，获取免费咨询和定制化解决方案建议</p>
        <router-link to="/contact" class="btn-primary btn-large">免费咨询</router-link>
      </div>
    </section>
  </div>
</template>

<script setup>
// 核心优势数据
const advantages = [
  {
    icon: '🚀',
    title: '技术领先',
    description: '采用最前沿的人工智能和大数据技术，确保解决方案的技术先进性'
  },
  {
    icon: '🔧',
    title: '灵活定制',
    description: '根据客户需求提供个性化定制服务，满足不同业务场景的需求'
  },
  {
    icon: '🛡️',
    title: '安全可靠',
    description: '严格的数据安全保护机制，确保客户信息和业务数据的安全'
  },
  {
    icon: '📈',
    title: '效果显著',
    description: '经过验证的解决方案，帮助客户提升效率、降低成本、增加收益'
  }
]

// 主要产品数据
const products = [
  {
    icon: '🧠',
    name: '人工智能平台',
    description: '基于深度学习的智能分析系统，实现精准预测和决策支持',
    features: ['机器学习算法库', '可视化分析界面', '实时数据处理', '模型自动优化'],
    link: '/products'
  },
  {
    icon: '📊',
    name: '大数据解决方案',
    description: '高效的大数据处理框架，支持海量数据的快速分析和挖掘',
    features: ['分布式计算架构', 'PB级数据处理', '多源数据接入', '实时流处理'],
    link: '/products'
  },
  {
    icon: '🌐',
    name: '物联网系统',
    description: '全面的物联网设备管理平台，实现设备的远程监控和智能控制',
    features: ['百万级设备接入', '设备状态监控', '远程固件升级', '安全通信协议'],
    link: '/products'
  },
  {
    icon: '☁️',
    name: '云计算服务',
    description: '稳定可靠的云计算基础设施，提供弹性计算和存储服务',
    features: ['99.99%可用性', '按需弹性扩展', '多层次安全防护', '全球节点部署'],
    link: '/products'
  }
]

// 客户案例数据
const cases = [
  {
    client: '某大型制造企业',
    industry: '制造业',
    title: '智能制造升级项目',
    summary: '通过部署华智科技的智能制造解决方案，实现了生产过程的全面数字化和智能化，大幅提升了生产效率和产品质量。',
    results: [
      { value: '35%', label: '生产效率提升' },
      { value: '50%', label: '故障率降低' },
      { value: '20%', label: '运营成本下降' }
    ],
    link: '/products'
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
    ],
    link: '/products'
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
    ],
    link: '/products'
  }
]

// 合作伙伴数据
const partners = [
  '阿里巴巴', '腾讯', '华为', '百度', '京东', '小米',
  '中国移动', '中国联通', '中国电信', '平安集团', '招商银行', '中国人寿'
]
</script>

<style scoped>
.home-page {
  overflow-x: hidden;
}

/* 首屏大图 */
.hero-section {
  display: flex;
  align-items: center;
  min-height: 600px;
  padding: 4rem 1rem;
  background: linear-gradient(135deg, #f0f4f8 0%, #e3f2fd 100%);
}

.hero-content {
  flex: 1;
  max-width: 600px;
}

.hero-content h1 {
  font-size: 2.5rem;
  color: #1a73e8;
  margin-bottom: 1rem;
  line-height: 1.2;
}

.hero-content p {
  color: #555;
  font-size: 1.1rem;
  margin-bottom: 2rem;
  line-height: 1.6;
}

.hero-buttons {
  display: flex;
  gap: 1rem;
}

.btn-primary,
.btn-secondary {
  padding: 0.8rem 1.8rem;
  border-radius: 6px;
  text-decoration: none;
  font-weight: 500;
  transition: all 0.3s ease;
}

.btn-primary {
  background: #1a73e8;
  color: white;
}

.btn-primary:hover {
  background: #1557b0;
  transform: translateY(-2px);
}

.btn-secondary {
  background: white;
  color: #1a73e8;
  border: 1px solid #1a73e8;
}

.btn-secondary:hover {
  background: #f0f4f8;
  transform: translateY(-2px);
}

.hero-image {
  flex: 1;
  display: flex;
  justify-content: center;
}

.hero-image img {
  max-width: 100%;
  height: auto;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.15);
}

/* 核心优势 */
.advantages-section {
  padding: 5rem 1rem;
  text-align: center;
}

.advantages-section h2 {
  font-size: 2rem;
  color: #1a73e8;
  margin-bottom: 3rem;
}

.advantages-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.advantage-card {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
}

.advantage-card .icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.advantage-card h3 {
  color: #1a73e8;
  margin-bottom: 1rem;
  font-size: 1.3rem;
}

.advantage-card p {
  color: #555;
  line-height: 1.6;
}

/* 主要产品 */
.products-section {
  padding: 5rem 1rem;
  background: #f8fafc;
}

.products-section h2 {
  text-align: center;
  font-size: 2rem;
  color: #1a73e8;
  margin-bottom: 3rem;
}

.products-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.product-card {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  text-align: center;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
  transition: transform 0.3s ease;
}

.product-card:hover {
  transform: translateY(-5px);
}

.product-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.product-card h3 {
  color: #1a73e8;
  margin-bottom: 1rem;
  font-size: 1.3rem;
}

.product-card p {
  color: #555;
  line-height: 1.6;
  margin-bottom: 1.5rem;
}

.features-list {
  text-align: left;
  margin-bottom: 1.5rem;
  padding-left: 1.5rem;
}

.features-list li {
  color: #555;
  margin-bottom: 0.5rem;
  position: relative;
}

.features-list li::before {
  content: '•';
  color: #1a73e8;
  font-weight: bold;
  position: absolute;
  left: -1rem;
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
.cases-section {
  padding: 5rem 1rem;
  text-align: center;
}

.cases-section h2 {
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

.case-card {
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

.case-card:hover .case-image img {
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

.case-stats {
  display: flex;
  justify-content: space-around;
  margin-bottom: 1.5rem;
  padding: 1rem 0;
  border-top: 1px solid #eee;
  border-bottom: 1px solid #eee;
}

.stat-item {
  text-align: center;
}

.stat-value {
  display: block;
  font-size: 1.5rem;
  font-weight: bold;
  color: #1a73e8;
  margin-bottom: 0.3rem;
}

.stat-label {
  font-size: 0.9rem;
  color: #666;
}

.view-case {
  display: inline-block;
  color: #1a73e8;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.3s ease;
}

.view-case:hover {
  color: #1557b0;
}

/* 技术实力 */
.tech-section {
  padding: 5rem 1rem;
  background: #f0f4f8;
}

.tech-section h2 {
  text-align: center;
  font-size: 2rem;
  color: #1a73e8;
  margin-bottom: 3rem;
}

.tech-content {
  display: flex;
  gap: 3rem;
  max-width: 1200px;
  margin: 0 auto;
  align-items: center;
}

.tech-text {
  flex: 1;
}

.tech-text h3 {
  color: #1a73e8;
  margin-bottom: 1rem;
  font-size: 1.5rem;
}

.tech-text p {
  color: #555;
  line-height: 1.6;
  margin-bottom: 1.5rem;
}

.tech-stats {
  display: flex;
  gap: 2rem;
  margin-top: 1.5rem;
}

.stat {
  text-align: center;
}

.number {
  display: block;
  font-size: 2rem;
  font-weight: bold;
  color: #1a73e8;
  margin-bottom: 0.3rem;
}

.label {
  font-size: 0.9rem;
  color: #666;
}

.tech-image {
  flex: 1;
}

.tech-image img {
  width: 100%;
  height: auto;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.15);
}

/* 合作伙伴 */
.partners-section {
  padding: 5rem 1rem;
  text-align: center;
}

.partners-section h2 {
  font-size: 2rem;
  color: #1a73e8;
  margin-bottom: 3rem;
}

.partners-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 2rem;
  max-width: 1200px;
  margin: 0 auto;
  align-items: center;
}

.partner-logo {
  opacity: 0.7;
  transition: opacity 0.3s ease;
}

.partner-logo:hover {
  opacity: 1;
}

.partner-logo img {
  max-width: 100%;
  height: 60px;
  object-fit: contain;
}

/* 行动号召 */
.cta-section {
  padding: 5rem 1rem;
  text-align: center;
  background: linear-gradient(135deg, #1a73e8 0%, #4285f4 100%);
  color: white;
}

.cta-content {
  max-width: 700px;
  margin: 0 auto;
}

.cta-content h2 {
  font-size: 2rem;
  margin-bottom: 1rem;
}

.cta-content p {
  margin-bottom: 2rem;
  opacity: 0.9;
}

.btn-large {
  padding: 1rem 2.5rem;
  font-size: 1.1rem;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .hero-section {
    flex-direction: column;
    text-align: center;
    min-height: auto;
    padding: 3rem 1rem;
  }

  .hero-content {
    margin-bottom: 2rem;
  }

  .hero-buttons {
    justify-content: center;
  }

  .tech-content {
    flex-direction: column;
  }

  .case-stats {
    flex-wrap: wrap;
    gap: 1rem;
  }

  .stat-value {
    font-size: 1.2rem;
  }
}
</style>