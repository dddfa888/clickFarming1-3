<!-- src/views/CompanyIntro.vue -->
<template>
  <div class="company-intro">
    <HeaderBar title="公司简介" />
    <div class="content-box">
      <p>
        Mercado Libre是由Marcos
        Galperín创立的。1999年3月，他在美国斯坦福大学商学院攻读工商管理硕士学位时构思了这个平台的概念。在完成大学毕业后，他得到YPF的资助，以便在公司财务部门工作的同时实施这个研究项目。然而，在他在大学期间，Repsol公司收购了YPF，Galperín所在的部门被关闭。这时，Galperín开始了为期三个月的研究和咨询过程，与其他教授一起分析在拉丁美洲建立在线市场的可能性。他的财务教授Jack
        McDonald是帮助他获得第一个投资者的人：John Muse。
      </p>
      <p>
        在获得学士学位之后，他花了一些时间创立公司，并于1999年8月2日向公众推出，迅速扩展到以下国家：阿根廷、巴西、哥伦比亚、智利、厄瓜多尔、墨西哥、秘鲁、乌拉圭和委内瑞拉。
      </p>
      <p>
        尽管遇到了困难，Mercado
        Libre已经进行了两轮融资。Galperin表示：“没有当地的风险投资公司，国际公司也不愿意关注拉丁美洲”。第一轮融资于1999年11月进行，第二轮融资于2000年5月进行。这些轮次的投资者包括：JP
        Morgan Partners、Flatiron Fund、Hicks、John Muse、Tate & Furst、Goldman
        Sachs、Fondo CRI、Banco Santander Central Hispano和GE
        Equity。根据Galperin在《时代杂志》上的说法：“我们必须从头开始建立一切。电子商务的物流和数字支付的基础设施：我们不得不建立一切。我们的一些国际竞争对手，如eBay和亚马逊，是从头开始发展的。有趣的是，我们在我们21周年纪念日之后开始了更快的发展。我们正在使拉丁美洲的金融和商业变得更加民主化。这是一个超过两个十年的一夜成功故事。
      </p>
      <!-- 可将长文本继续补充 -->
    </div>
  </div>
</template>

<script setup>
import HeaderBar from "../../components/HeaderBar.vue";
</script>

<style scoped>
.company-intro {
  background: url("../../assets/img/background-D7o_xTde.png") no-repeat center
    center;
  background-size: cover;
  height: 100vh;
  overflow-y: auto;
}

.content-box {
  background-color: transparent !important;
  -webkit-backdrop-filter: blur(6px) !important;
  backdrop-filter: blur(6px) !important;
  margin: 12px;
  padding: 12px;
  border-radius: 10px;
  border: 1px solid #7a797d;
  color: white;
  line-height: 1.8;
  font-size: 14px;
}
</style>
