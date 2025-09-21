<template>
  <div class="contact-page">
    <section class="contact-header">
      <h2>联系我们</h2>
      <p>我们随时准备为您提供专业的咨询和服务</p>
    </section>

    <div class="contact-container">
      <!-- 联系信息 -->
      <section class="contact-info">
        <div class="info-card" v-for="(info, index) in contactInfo" :key="index">
          <div class="icon">{{ info.icon }}</div>
          <h3>{{ info.title }}</h3>
          <p v-for="(detail, dIndex) in info.details" :key="dIndex">{{ detail }}</p>
        </div>
      </section>

      <!-- 联系表单 -->
      <section class="contact-form">
        <h3>发送消息</h3>
        <form @submit.prevent="submitForm">
          <div class="form-group">
            <label for="name">姓名 *</label>
            <input 
              type="text" 
              id="name" 
              v-model="formData.name" 
              required 
            />
          </div>
          
          <div class="form-group">
            <label for="email">邮箱 *</label>
            <input 
              type="email" 
              id="email" 
              v-model="formData.email" 
              required 
            />
          </div>
          
          <div class="form-group">
            <label for="phone">电话</label>
            <input 
              type="tel" 
              id="phone" 
              v-model="formData.phone" 
            />
          </div>
          
          <div class="form-group">
            <label for="company">公司</label>
            <input 
              type="text" 
              id="company" 
              v-model="formData.company" 
            />
          </div>
          
          <div class="form-group">
            <label for="subject">主题 *</label>
            <select 
              id="subject" 
              v-model="formData.subject" 
              required
            >
              <option value="">请选择主题</option>
              <option value="咨询">产品咨询</option>
              <option value="合作">商务合作</option>
              <option value="技术支持">技术支持</option>
              <option value="其他">其他问题</option>
            </select>
          </div>
          
          <div class="form-group">
            <label for="message">消息内容 *</label>
            <textarea 
              id="message" 
              v-model="formData.message" 
              rows="5" 
              required
            ></textarea>
          </div>
          
          <button type="submit" class="submit-btn" :disabled="isSubmitting">
            {{ isSubmitting ? '提交中...' : '发送消息' }}
          </button>
        </form>
      </section>
    </div>

    <!-- 地图 -->
    <section class="map-section">
      <h3>我们的位置</h3>
      <div class="map-container">
        <img src="https://picsum.photos/1200/400" alt="公司位置地图" />
      </div>
    </section>

    <!-- 常见问题 -->
    <section class="faq-section">
      <h3>常见问题</h3>
      <div class="faq-list">
        <div 
          class="faq-item" 
          v-for="(faq, index) in faqs" 
          :key="index"
          @click="toggleFaq(index)"
        >
          <div class="faq-question">
            <h4>{{ faq.question }}</h4>
            <span class="faq-toggle">{{ openFaq === index ? '−' : '+' }}</span>
          </div>
          <div class="faq-answer" v-show="openFaq === index">
            <p>{{ faq.answer }}</p>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref } from 'vue'

// 联系信息
const contactInfo = [
  {
    icon: '📞',
    title: '联系电话',
    details: ['400-123-4567', '021-88888888']
  },
  {
    icon: '✉️',
    title: '电子邮箱',
    details: ['sales@huazhi.com', 'support@huazhi.com', 'hr@huazhi.com']
  },
  {
    icon: '🏢',
    title: '公司地址',
    details: [
      '上海市浦东新区张江高科技园区',
      '科苑路88号，华智大厦',
      '邮编：201210'
    ]
  },
  {
    icon: '🕒',
    title: '工作时间',
    details: ['周一至周五：9:00 - 18:00', '周六：9:00 - 12:00', '周日：休息']
  }
]

// 表单数据
const formData = ref({
  name: '',
  email: '',
  phone: '',
  company: '',
  subject: '',
  message: ''
})

const isSubmitting = ref(false)

// 提交表单
const submitForm = () => {
  if (isSubmitting.value) return
  
  isSubmitting.value = true
  
  // 模拟提交过程
  setTimeout(() => {
    alert('感谢您的留言！我们的工作人员将在24小时内与您联系。')
    formData.value = {
      name: '',
      email: '',
      phone: '',
      company: '',
      subject: '',
      message: ''
    }
    isSubmitting.value = false
  }, 1500)
}

// 常见问题
const faqs = [
  {
    question: '你们的产品支持定制开发吗？',
    answer: '是的，我们提供全面的定制开发服务。根据客户的具体需求，我们可以对现有产品进行功能扩展或开发全新的解决方案。我们的技术团队将与您密切沟通，确保最终产品完全满足您的业务需求。'
  },
  {
    question: '如何获取产品的试用版本？',
    answer: '您可以通过以下方式获取产品试用：1) 在产品页面点击“申请试用”按钮；2) 联系我们的销售团队400-123-4567；3) 发送邮件至sales@huazhi.com。我们通常在1个工作日内为您开通试用账号。'
  },
  {
    question: '你们的技术支持服务包括哪些内容？',
    answer: '我们的技术支持服务包括：7x24小时在线支持、现场技术支持、定期系统维护、性能优化建议、安全漏洞修复等。购买产品后，您将获得为期一年的免费技术支持服务，之后可根据需要续费。'
  },
  {
    question: '产品的部署方式有哪些？',
    answer: '我们支持多种部署方式：1) 本地化部署：在客户自有服务器上安装；2) 云平台部署：部署在主流云服务商（如阿里云、腾讯云）上；3) 混合部署：关键数据本地存储，非敏感数据云端处理。我们将根据您的安全要求和IT基础设施推荐最适合的部署方案。'
  },
  {
    question: '如何成为你们的合作伙伴？',
    answer: '欢迎加入我们的合作伙伴计划！您可以通过partner@huazhi.com联系我们，或拨打商务合作热线。我们为合作伙伴提供产品培训、市场支持、销售激励等全方位支持。审核通过后，您将成为我们的授权代理商或解决方案提供商。'
  }
]

// 当前展开的FAQ
const openFaq = ref(null)

// 切换FAQ展开状态
const toggleFaq = (index) => {
  openFaq.value = openFaq.value === index ? null : index
}
</script>

<style scoped>
.contact-page {
  padding: 2rem 0;
}

.contact-header {
  text-align: center;
  margin-bottom: 2.5rem;
}

.contact-header h2 {
  font-size: 2rem;
  color: #1a73e8;
  margin-bottom: 1rem;
}

.contact-header p {
  color: #666;
  font-size: 1.1rem;
}

.contact-container {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 3rem;
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 1rem;
}

/* 联系信息 */
.contact-info {
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}

.info-card {
  background: white;
  padding: 1.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
}

.info-card .icon {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.info-card h3 {
  color: #1a73e8;
  margin-bottom: 0.8rem;
  font-size: 1.3rem;
}

.info-card p {
  color: #555;
  line-height: 1.6;
  margin-bottom: 0.5rem;
}

/* 联系表单 */
.contact-form {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
}

.contact-form h3 {
  color: #1a73e8;
  margin-bottom: 1.5rem;
  font-size: 1.5rem;
}

.form-group {
  margin-bottom: 1.2rem;
}

.form-group label {
  display: block;
  color: #333;
  margin-bottom: 0.5rem;
  font-weight: 500;
}

.form-group input,
.form-group select,
.form-group textarea {
  width: 100%;
  padding: 0.8rem;
  border: 1px solid #ddd;
  border-radius: 6px;
  font-size: 1rem;
  transition: border-color 0.3s ease;
}

.form-group input:focus,
.form-group select:focus,
.form-group textarea:focus {
  outline: none;
  border-color: #1a73e8;
}

.form-group select {
  cursor: pointer;
}

.submit-btn {
  background: #1a73e8;
  color: white;
  border: none;
  padding: 0.8rem 1.8rem;
  font-size: 1rem;
  border-radius: 6px;
  cursor: pointer;
  transition: background 0.3s ease;
  width: 100%;
  margin-top: 1rem;
}

.submit-btn:hover:not(:disabled) {
  background: #1557b0;
}

.submit-btn:disabled {
  background: #ccc;
  cursor: not-allowed;
}

/* 地图 */
.map-section {
  margin: 3rem 0;
  text-align: center;
}

.map-section h3 {
  font-size: 1.8rem;
  color: #1a73e8;
  margin-bottom: 1.5rem;
}

.map-container {
  max-width: 1200px;
  margin: 0 auto;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
}

.map-container img {
  width: 100%;
  height: 400px;
  object-fit: cover;
}

/* 常见问题 */
.faq-section {
  padding: 3rem 1rem;
  background: #f0f4f8;
  margin: 3rem 0;
}

.faq-section h3 {
  text-align: center;
  font-size: 1.8rem;
  color: #1a73e8;
  margin-bottom: 2rem;
}

.faq-list {
  max-width: 800px;
  margin: 0 auto;
}

.faq-item {
  background: white;
  margin-bottom: 1rem;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 2px 8px rgba(0,0,0,0.1);
  cursor: pointer;
}

.faq-question {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 1.2rem;
  background: #f8fafc;
  font-weight: 500;
  color: #1a73e8;
}

.faq-toggle {
  font-size: 1.5rem;
  font-weight: bold;
  width: 24px;
  height: 24px;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  background: #1a73e8;
  color: white;
}

.faq-answer {
  padding: 1.2rem;
  color: #555;
  line-height: 1.6;
  border-top: 1px solid #eee;
}

/* 响应式设计 */
@media (max-width: 768px) {
  .contact-container {
    grid-template-columns: 1fr;
  }

  .contact-form {
    padding: 1.5rem;
  }

  .map-container img {
    height: 300px;
  }
}
</style>