<template>
  <div class="company-profile">
    <!-- 标题部分 -->
    <div class="profile-header">
      <h1>公司简介</h1>
    </div>

    <!-- 内容部分 -->
    <div class="profile-content">
      <p
        v-for="(paragraph, index) in contentParagraphs"
        :key="index"
        class="content-paragraph"
      >
        {{ paragraph }}
      </p>
    </div>

    <!-- 引用部分 -->
    <div class="profile-quote">
      <blockquote>
        "我们必须从头开始建立一切。电子商务的物流和数字支付的基础设施：我们不得不建立一切。我们的一些国际竞争对手，如eBay和亚马逊，是从头开始发展的。有趣的是，我们在我们的21周年纪念日之后开始了更快的发展。我们在使拉丁美洲的金融和商业变得更加民主化。这是一个超过两个十年的一夜成功故事。"
      </blockquote>
      <div class="quote-author">— Marcos Galperín, 《时代杂志》</div>
    </div>
  </div>
</template>

<script setup>
// 将内容分割为段落
// const contentParagraphs = [
//   "Mercado Libre是由Marcos Galperín创立的。1999年3月，他在美国斯坦福大学商学院攻读工商管理硕士学位时构思了这个平台的概念。在完成大学毕业后，他得到YPF的资助，以便在公司财务部门工作的同时实施这个研究项目。然而，在他任大学期间，Repsol公司收购了YPF，Galperín所在的部门被关闭。这时，Galperín开始了为期三个月的研究和咨询过程，与其他教授一起分析在拉丁美洲建立在线市场的可能性。他的财务教授Jack McDonald是帮助他获得第一个投资者的人：John Muse。",

//   "在获得学士学位之后，他花了一些时间创立公司，并于1999年8月2日向公众推出，迅速扩展到以下国家：阿根廷、巴西、哥伦比亚、智利、厄瓜多尔、墨西哥、秘鲁、乌拉圭和塞内瑞拉。",

//   "尽管遇到了困难，Mercado Libre已经进行了两轮融资。Galperín表示："没有当地的风险投资公司，国际公司也不愿意关注拉丁美洲"。第一轮融资于1999年11月进行，第二轮融资于2000年5月进行。这些轮次的投资者包括：JP Morgan Partners、Hatiron Fund、Hicks、John Muse、Tate & Furst、Goldman Sachs、Fondo CRI、Banco Santander Central Hispano和GE Equity。根据Galperín在《时代杂志》上的说法："
// ]
</script>

<style scoped>
.company-profile {
  max-width: 800px;
  margin: 0 auto;
  padding: 30px 20px;
  font-family: "PingFang SC", "Microsoft YaHei", "Helvetica Neue", Arial,
    sans-serif;
  color: #333;
  line-height: 1.8;
}

.profile-header {
  text-align: center;
  margin-bottom: 30px;
  padding-bottom: 15px;
  border-bottom: 1px solid #e0e0e0;
}

.profile-header h1 {
  font-size: 28px;
  font-weight: bold;
  color: #2c3e50;
}

.profile-content {
  margin-bottom: 30px;
}

.content-paragraph {
  margin-bottom: 20px;
  text-align: justify;
  text-justify: inter-ideograph;
}

.profile-quote {
  background-color: #f9f9f9;
  border-left: 4px solid #3498db;
  padding: 20px;
  margin: 30px 0;
  border-radius: 0 5px 5px 0;
}

.profile-quote blockquote {
  margin: 0 0 10px 0;
  font-style: italic;
  color: #555;
  font-size: 16px;
}

.quote-author {
  text-align: right;
  font-weight: bold;
  color: #2c3e50;
}

@media (max-width: 768px) {
  .company-profile {
    padding: 20px 15px;
  }

  .profile-header h1 {
    font-size: 24px;
  }

  .content-paragraph {
    font-size: 15px;
  }

  .profile-quote blockquote {
    font-size: 15px;
  }
}
</style>
