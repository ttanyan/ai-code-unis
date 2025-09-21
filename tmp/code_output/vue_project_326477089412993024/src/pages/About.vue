<template>
  <div class="about-page">
    <!-- 公司简介 -->
    <section class="company-intro">
      <div class="intro-content">
        <h2>关于华智科技</h2>
        <p>华智科技成立于2015年，是一家专注于人工智能、大数据、物联网和云计算等前沿技术的高科技企业。我们致力于通过技术创新推动各行业的数字化转型，为客户提供全面的智能化解决方案。</p>
        <p>公司总部位于上海张江高科技园区，在北京、深圳、杭州设有分支机构，服务网络覆盖全国。经过多年的快速发展，华智科技已成为行业领先的智能化解决方案提供商，客户遍及金融、制造、医疗、零售等多个领域。</p>
      </div>
      <div class="intro-image">
        <img src="https://picsum.photos/800/600" alt="公司办公环境" />
      </div>
    </section>

    <!-- 发展历程 -->
    <section class="timeline-section">
      <h2>发展历程</h2>
      <div class="timeline">
        <div 
          class="timeline-item" 
          v-for="(item, index) in timeline" 
          :key="index"
          :class="{ 'left': index % 2 === 0, 'right': index % 2 === 1 }"
        >
          <div class="timeline-dot"></div>
          <div class="timeline-date">{{ item.year }}</div>
          <div class="timeline-content">
            <h3>{{ item.title }}</h3>
            <p>{{ item.description }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- 核心团队 -->
    <section class="team-section">
      <h2>核心团队</h2>
      <div class="team-grid">
        <div class="team-member" v-for="(member, index) in teamMembers" :key="index">
          <div class="member-image">
            <img :src="`https://picsum.photos/seed/team${index}/300/400`" :alt="member.name" />
            <div class="social-links">
              <a :href="member.socialLinks.linkedin" target="_blank" aria-label="LinkedIn">
                <i>in</i>
              </a>
              <a :href="member.socialLinks.weibo" target="_blank" aria-label="Weibo">
                <i>wb</i>
              </a>
            </div>
          </div>
          <div class="member-info">
            <h3>{{ member.name }}</h3>
            <p class="position">{{ member.position }}</p>
            <p class="bio">{{ member.bio }}</p>
          </div>
        </div>
      </div>
    </section>

    <!-- 企业文化 -->
    <section class="culture-section">
      <h2>企业文化</h2>
      <div class="culture-content">
        <div class="values">
          <h3>核心价值观</h3>
          <div class="values-grid">
            <div class="value-card" v-for="(value, index) in coreValues" :key="index">
              <div class="icon">{{ value.icon }}</div>
              <h4>{{ value.title }}</h4>
              <p>{{ value.description }}</p>
            </div>
          </div>
        </div>
        
        <div class="mission-vision">
          <div class="mission">
            <h3>使命</h3>
            <p>通过技术创新推动社会进步，让智能科技惠及更多人。</p>
          </div>
          <div class="vision">
            <h3>愿景</h3>
            <p>成为全球领先的智能化解决方案提供商，引领科技未来发展。</p>
          </div>
        </div>
      </div>
    </section>

    <!-- 荣誉资质 -->
    <section class="awards-section">
      <h2>荣誉与资质</h2>
      <div class="awards-grid">
        <div class="award-card" v-for="(award, index) in awards" :key="index">
          <div class="award-icon">🏆</div>
          <h3>{{ award.title }}</h3>
          <p class="year">{{ award.year }}</p>
          <p class="description">{{ award.description }}</p>
        </div>
      </div>
    </section>

    <!-- 加入我们 -->
    <section class="careers-section">
      <div class="careers-content">
        <h2>加入我们</h2>
        <p>华智科技是一个充满活力和创新精神的团队，我们欢迎有志于智能科技领域的优秀人才加入。在这里，您将有机会参与前沿技术的研发，与行业专家共事，实现个人价值的最大化。</p>
        <button class="btn-careers">查看职位空缺</button>
      </div>
    </section>
  </div>
</template>

<script setup>
// 发展历程数据
const timeline = [
  {
    year: '2015',
    title: '公司成立',
    description: '华智科技在上海正式成立，专注于人工智能技术的研发和应用，初期团队由5名核心技术人员组成。'
  },
  {
    year: '2016',
    title: '首轮融资成功',
    description: '完成数百万美元的天使轮融资，投资方包括知名风险投资机构，为公司的技术研发提供了资金支持。'
  },
  {
    year: '2017',
    title: '产品发布',
    description: '推出首款人工智能分析平台，成功应用于金融风控领域，获得首批重要客户认可。'
  },
  {
    year: '2018',
    title: 'A轮融资',
    description: '完成5000万美元A轮融资，估值达到5亿美元，开始在全国范围内扩展业务。'
  },
  {
    year: '2019',
    title: '设立研发中心',
    description: '在北京和深圳设立研发中心，吸引高端技术人才，加强在大数据和物联网领域的研发投入。'
  },
  {
    year: '2020',
    title: 'B轮融资',
    description: '完成1.5亿美元B轮融资，与多家世界500强企业建立战略合作关系，业务拓展至海外市场。'
  },
  {
    year: '2021',
    title: '上市计划启动',
    description: '正式启动上市计划，完成股份制改造，聘请国际知名投行作为上市顾问。'
  },
  {
    year: '2022',
    title: '年度营收突破',
    description: '公司年度总营收突破10亿元人民币，服务客户超过1000家，员工规模达到800人。'
  },
  {
    year: '2023',
    title: '技术创新奖',
    description: '荣获“国家科技进步二等奖”，在人工智能算法领域取得重大突破，技术实力得到权威认可。'
  }
]

// 核心团队数据
const teamMembers = [
  {
    name: '张明远',
    position: '创始人兼CEO',
    bio: '前谷歌高级工程师，人工智能领域专家，在机器学习和深度学习方面有深厚造诣，拥有多项国际专利。',
    socialLinks: {
      linkedin: '#',
      weibo: '#'
    }
  },
  {
    name: '李思琪',
    position: '首席技术官',
    bio: '清华大学计算机博士，曾任阿里巴巴技术总监，在分布式系统和大数据处理方面有丰富经验。',
    socialLinks: {
      linkedin: '#',
      weibo: '#'
    }
  },
  {
    name: '王建国',
    position: '首席科学家',
    bio: 'IEEE Fellow，国际知名的人工智能专家，在自然语言处理和计算机视觉领域有突出贡献。',
    socialLinks: {
      linkedin: '#',
      weibo: '#'
    }
  },
  {
    name: '陈晓雯',
    position: '首席运营官',
    bio: '前IBM大中华区副总裁，拥有20年企业管理经验，在战略规划和运营管理方面有卓越能力。',
    socialLinks: {
      linkedin: '#',
      weibo: '#'
    }
  }
]

// 核心价值观
const coreValues = [
  {
    icon: '💡',
    title: '创新驱动',
    description: '我们相信创新是企业发展的根本动力，鼓励员工勇于尝试，不断突破技术边界。'
  },
  {
    icon: '🤝',
    title: '客户至上',
    description: '客户成功是我们最大的追求，我们始终以客户需求为导向，提供超越期望的服务。'
  },
  {
    icon: '🚀',
    title: '追求卓越',
    description: '我们对产品质量和技术水平有着极致的追求，力求在每个细节上都做到最好。'
  },
  {
    icon: '🌱',
    title: '持续成长',
    description: '我们重视员工的个人发展，提供丰富的学习机会和职业发展空间。'
  },
  {
    icon: '🌐',
    title: '开放协作',
    description: '我们倡导开放的文化，鼓励跨部门协作，共同解决问题，分享知识和经验。'
  },
  {
    icon: '🎯',
    title: '结果导向',
    description: '我们注重实际成果，以数据和事实为基础，确保每一项工作都能产生价值。'
  }
]

// 荣誉资质
const awards = [
  {
    title: '国家科技进步二等奖',
    year: '2023',
    description: '表彰在人工智能算法领域的重大技术突破和创新成果。'
  },
  {
    title: '中国人工智能领军企业',
    year: '2022',
    description: '由中国人工智能学会评选，肯定公司在行业内的领导地位。'
  },
  {
    title: '高新技术企业认证',
    year: '2021',
    description: '由国家科技部认定，证明公司在技术创新方面的实力。'
  },
  {
    title: 'ISO 9001质量管理体系认证',
    year: '2020',
    description: '国际标准化组织认证，确保产品和服务质量达到国际标准。'
  },
  {
    title: '最佳雇主奖',
    year: '2019',
    description: '由智联招聘评选，认可公司在员工关怀和发展方面的卓越表现。'
  },
  {
    title: '最具投资价值企业',
    year: '2018',
    description: '在创新创业大赛中获得评委一致认可，展现巨大发展潜力。'
  }
]
</script>

<style scoped>
.about-page {
  overflow-x: hidden;
}

/* 公司简介 */
.company-intro {
  display: flex;
  align-items: center;
  padding: 5rem 1rem;
  max-width: 1200px;
  margin: 0 auto;
}

.intro-content {
  flex: 1;
  padding-right: 2rem;
}

.intro-content h2 {
  font-size: 2rem;
  color: #1a73e8;
  margin-bottom: 1.5rem;
}

.intro-content p {
  color: #555;
  line-height: 1.6;
  margin-bottom: 1rem;
}

.intro-image {
  flex: 1;
}

.intro-image img {
  width: 100%;
  height: auto;
  border-radius: 12px;
  box-shadow: 0 10px 30px rgba(0,0,0,0.15);
}

/* 发展历程 */
.timeline-section {
  padding: 5rem 1rem;
  background: #f8fafc;
}

.timeline-section h2 {
  text-align: center;
  font-size: 2rem;
  color: #1a73e8;
  margin-bottom: 3rem;
}

.timeline {
  position: relative;
  max-width: 1000px;
  margin: 0 auto;
}

.timeline::before {
  content: '';
  position: absolute;
  left: 50%;
  top: 0;
  bottom: 0;
  width: 2px;
  background: #1a73e8;
  transform: translateX(-50%);
}

.timeline-item {
  position: relative;
  width: 50%;
  padding: 1rem 2rem;
  box-sizing: border-box;
}

.timeline-item.left {
  left: 0;
  text-align: right;
}

.timeline-item.right {
  left: 50%;
  text-align: left;
}

.timeline-dot {
  position: absolute;
  top: 1.5rem;
  width: 16px;
  height: 16px;
  border-radius: 50%;
  background: white;
  border: 3px solid #1a73e8;
  z-index: 1;
}

.timeline-item.left .timeline-dot {
  right: -8px;
}

.timeline-item.right .timeline-dot {
  left: -8px;
}

.timeline-date {
  position: absolute;
  top: 0;
  width: 80px;
  padding: 0.4rem;
  background: #1a73e8;
  color: white;
  border-radius: 6px;
  font-weight: bold;
  text-align: center;
}

.timeline-item.left .timeline-date {
  right: 0;
}

.timeline-item.right .timeline-date {
  left: 0;
}

.timeline-content {
  background: white;
  padding: 1.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
  margin: 1rem 0;
}

.timeline-content h3 {
  color: #1a73e8;
  margin-bottom: 0.8rem;
  font-size: 1.2rem;
}

.timeline-content p {
  color: #555;
  line-height: 1.6;
}

/* 核心团队 */
.team-section {
  padding: 5rem 1rem;
}

.team-section h2 {
  text-align: center;
  font-size: 2rem;
  color: #1a73e8;
  margin-bottom: 3rem;
}

.team-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.team-member {
  background: white;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
  transition: transform 0.3s ease;
}

.team-member:hover {
  transform: translateY(-5px);
}

.member-image {
  position: relative;
  height: 400px;
  overflow: hidden;
}

.member-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.team-member:hover .member-image img {
  transform: scale(1.05);
}

.social-links {
  position: absolute;
  bottom: 1rem;
  left: 1rem;
  right: 1rem;
  display: flex;
  gap: 1rem;
  opacity: 0;
  transition: opacity 0.3s ease;
}

.team-member:hover .social-links {
  opacity: 1;
}

.social-links a {
  flex: 1;
  background: rgba(255,255,255,0.9);
  color: #333;
  text-decoration: none;
  padding: 0.6rem;
  border-radius: 6px;
  text-align: center;
  font-style: normal;
  transition: background 0.3s ease;
}

.social-links a:hover {
  background: #1a73e8;
  color: white;
}

.member-info {
  padding: 1.5rem;
}

.member-info h3 {
  color: #1a73e8;
  margin-bottom: 0.5rem;
  font-size: 1.2rem;
}

.position {
  color: #1a73e8;
  font-weight: 500;
  margin-bottom: 0.8rem;
  font-size: 0.9rem;
}

.bio {
  color: #555;
  line-height: 1.6;
}

/* 企业文化 */
.culture-section {
  padding: 5rem 1rem;
  background: #f0f4f8;
}

.culture-section h2 {
  text-align: center;
  font-size: 2rem;
  color: #1a73e8;
  margin-bottom: 3rem;
}

.culture-content {
  display: flex;
  gap: 3rem;
  max-width: 1200px;
  margin: 0 auto;
}

.values {
  flex: 2;
}

.values h3 {
  color: #1a73e8;
  margin-bottom: 1.5rem;
  font-size: 1.5rem;
  text-align: center;
}

.values-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1.5rem;
}

.value-card {
  background: white;
  padding: 1.5rem;
  border-radius: 12px;
  text-align: center;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
}

.value-card .icon {
  font-size: 2.5rem;
  margin-bottom: 1rem;
}

.value-card h4 {
  color: #1a73e8;
  margin-bottom: 0.8rem;
  font-size: 1.1rem;
}

.value-card p {
  color: #555;
  font-size: 0.9rem;
  line-height: 1.5;
}

.mission-vision {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.mission, .vision {
  background: white;
  padding: 1.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
}

.mission h3, .vision h3 {
  color: #1a73e8;
  margin-bottom: 0.8rem;
  font-size: 1.2rem;
  text-align: center;
}

.mission p, .vision p {
  color: #555;
  line-height: 1.6;
}

/* 荣誉资质 */
.awards-section {
  padding: 5rem 1rem;
}

.awards-section h2 {
  text-align: center;
  font-size: 2rem;
  color: #1a73e8;
  margin-bottom: 3rem;
}

.awards-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
  max-width: 1200px;
  margin: 0 auto;
}

.award-card {
  background: white;
  padding: 2rem;
  border-radius: 12px;
  text-align: center;
  box-shadow: 0 4px 15px rgba(0,0,0,0.1);
}

.award-icon {
  font-size: 3rem;
  margin-bottom: 1rem;
}

.award-card h3 {
  color: #1a73e8;
  margin-bottom: 0.8rem;
  font-size: 1.2rem;
}

.year {
  font-size: 1.2rem;
  font-weight: bold;
  color: #1a73e8;
  margin-bottom: 0.8rem;
}

.description {
  color: #555;
  line-height: 1.6;
}

/* 加入我们 */
.careers-section {
  padding: 5rem 1rem;
  background: linear-gradient(135deg, #1a73e8 0%, #4285f4 100%);
  color: white;
  text-align: center;
}

.careers-content {
  max-width: 700px;
  margin: 0 auto;
}

.careers-content h2 {
  font-size: 2rem;
  margin-bottom: 1rem;
}

.careers-content p {
  margin-bottom: 2rem;
  opacity: 0.9;
}

.btn-careers {
  background: white;
  color: #1a73e8;
  border: none;
  padding: 0.8rem 2rem;
  font-size: 1rem;
  border-radius: 6px;
  cursor: pointer;
  transition: all 0.3s ease;
}

.btn-careers:hover {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0,0,0,0.2);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .company-intro {
    flex-direction: column;
    text-align: center;
  }

  .intro-content {
    padding-right: 0;
    margin-bottom: 2rem;
  }

  .culture-content {
    flex-direction: column;
  }

  .mission-vision {
    flex-direction: row;
  }

  .timeline::before {
    left: 20px;
  }

  .timeline-item {
    width: 100%;
    left: 0;
    text-align: left;
    padding-left: 60px;
    padding-right: 2rem;
  }

  .timeline-item.left .timeline-dot,
  .timeline-item.right .timeline-dot {
    left: 12px;
    right: auto;
  }

  .timeline-item.left .timeline-date,
  .timeline-item.right .timeline-date {
    left: 50px;
    right: auto;
  }
}
</style>