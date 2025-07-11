<template>
  <div class="order-detail-container">
    <div class="order-header">
      <div>
        <p style="font-size: 15px">{{ t("订单详细信息") }}</p>
        <div class="data-provider">{{ t("数据提供者 Mercado Asia") }}</div>
      </div>
      <div class="amount-section">
        <div class="amount-display">
          {{ formatCurrency(order.userBalance) }}
        </div>
        <div class="amount-label">{{ t("剩余") }}(€)</div>
      </div>
    </div>

    <div class="order-summary">
      <img src="../../assets/img/banner1-8QRSYmQj.png" alt="" />
      <div class="status-badge" @click="Sendbutton">
        {{ t("收到") }}
      </div>
    </div>

    <div class="order-details-grid">
      <div class="detail-item">
        <div class="detail-label">{{ t("剩余") }}</div>
        <div class="detail-value">{{ formatCurrency(order.userBalance) }}</div>
      </div>
      <div class="detail-item">
        <div class="detail-label">{{ t("当前水平") }}</div>
        <div class="detail-value">{{ order.userLevel }}</div>
      </div>
      <div class="detail-item">
        <div class="detail-label">{{ t("订单已付款") }}</div>
        <div class="detail-value">{{ order.orderNum }}</div>
      </div>
      <div class="detail-item">
        <div class="detail-label">{{ t("昨天折扣") }}</div>
        <div class="detail-value">{{ order.numYesterday }}€</div>
      </div>
      <div class="detail-item">
        <div class="detail-label">{{ t("今天折扣") }}</div>
        <div class="detail-value">{{ order.numToday }}€</div>
      </div>
    </div>

    <div class="foundation-rules">
      <p>{{ t("介绍基金会规则") }}</p>
      <div class="rules-content" v-if="locale === 'vi'">
        <!-- 这里可以添加基金会规则的具体内容 -->
        <p>
          Khi bạn trở thành thành viên Mercado Asia, bạn sẽ nhận được các mã sản
          phẩm có liên quan về đơn đặt hàng , bao gồm thông tin sản phẩm chi
          tiết đơn hàng , giá trị sản phẩm , số lượng ...vv..
        </p>
        <p>
          Thành viên của Mercado Asia sẽ là nhà trung gian giúp xác nhận đơn
          hàng giữa các NHÀ SẢN XUẤT & QUÝ ĐỐI TÁC ( người đặt mua ).
        </p>
        <p>
          Thành viên của Mercado Asia sẽ là nhà trung gian giúp xác nhận đơn
          hàng giữa các NHÀ SẢN XUẤT & QUÝ ĐỐI TÁC ( người đặt mua ).
        </p>
      </div>
    </div>
    <ProductModal
      v-if="showModal"
      :id="id"
      @close="showModal = false"
      @pay="handlePay"
    />
  </div>
</template>

<script setup>
import { ref } from "vue";
import {
  createOrder,
  getUserGradeAndBalanceAndDiscount,
  sendDistribution,
} from "../../api";
import { useI18n } from "vue-i18n";
import ProductModal from "../../components/ProductModal.vue";
import { useLangStore } from "../../store/useLangStore";
import { storeToRefs } from "pinia";
import { notify } from "../../utils/notify.js";
import router from "../../router/index.js";

const { t } = useI18n();
const order = ref({});
const showModal = ref(false);
const id = ref(null);
const langStore = useLangStore();
const { locale } = storeToRefs(langStore);

const formatCurrency = (value) => {
  if (typeof value !== "number") return "0 €";
  return value.toLocaleString("de-DE", { style: "currency", currency: "EUR" });
};

const Sendbutton = () => {
  if (!order.value.withdrawalAddress) {
    notify({
      title: t("通知"),
      message: t("地址未填写,请填写完整"),
      type: "warning",
      duration: 2000,
    });
    router.push({ path: "/address" });
    return;
  }
  createOrder().then((res) => {
    console.log(res.orderId);
    if (res.code === 200) {
      showModal.value = true;
      id.value = res.orderId;
    } else {
      notify({
        title: t("通知"),
        message: t(res.msg),
        type: "error",
        duration: 2000,
      });
    }
  });
};

const handlePay = () => {
  showModal.value = false;
  sendDistribution(id.value).then((res) => {
    console.log(res);
    if (res.code === 200) {
      notify({
        title: t("通知"),
        message: t(res.msg),
        type: "success",
        duration: 2000,
      });
      // 支付成功后刷新余额
      getUserGradeAndBalanceAndDiscount().then((refreshRes) => {
        order.value = refreshRes.data;
      });
    } else {
      notify({
        title: t("通知"),
        message: t(res.msg),
        type: "error",
        duration: 2000,
      });
    }
  });
};

getUserGradeAndBalanceAndDiscount().then((res) => {
  console.log(res.data);
  order.value = res.data;
});
</script>

<style scoped>
.order-detail-container {
  font-family: Arial, sans-serif;
  max-width: 800px;
  margin: 0 auto;
  color: #fff;
  padding: 10px;
  height: 100vh;
  background: url("../../assets/img/BG-nhandon-B-V7rk8F.png") no-repeat center
    center fixed;
}

.order-header {
  margin-bottom: 20px;
  padding-bottom: 15px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  color: #fff;
}

.data-provider {
  font-size: 0.9rem;
  color: #fff;
  margin-top: 5px;
}

.order-summary {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-bottom: 25px;
  padding: 5px;
  border-radius: 6px;
}
.order-summary > img {
  width: 100%;
}

.amount-section {
  text-align: center;
}

.amount-display {
  color: #fff;
}

.amount-label {
  font-size: 0.9rem;
  color: #fff;
}

.status-badge {
  padding: 6px 59px;
  background-color: #0262b0;
  color: white;
  border-radius: 10px;
  font-size: 0.9rem;
  font-weight: bold;
  margin-top: 30px;
}

.order-details-grid {
  padding: 10px;
  border: 1px solid #7a797d;
  border-radius: 10px;
  background-color: transparent;
  backdrop-filter: blur(5px);
  -webkit-backdrop-filter: blur(5px);
}

.detail-item {
  display: flex;
  justify-content: space-between;
}

.detail-label {
  font-size: 0.85rem;
  color: #fff;
  margin-bottom: 5px;
}

.detail-value {
  font-size: 1.1rem;
}

.foundation-rules {
  margin-top: 15px;
  padding: 10px;
  background-color: transparent;
  backdrop-filter: blur(5px);
  border: 1px solid #7a797d;
  border-radius: 10px;
  -webkit-backdrop-filter: blur(5px);
  text-align-last: center;
  font-size: 0.79rem;
}

.rules-content {
  font-size: 0.79rem;
  color: #fff;
  margin-top: 30px;
}

@media screen and (min-width: 768px) {
  .order-detail-container {
    font-family: Arial, sans-serif;
    width: 520px;
    margin: 0 auto;
    color: #fff;
    padding: 10px;
    height: 100vh;
    background: url("../../assets/img/BG-nhandon-B-V7rk8F.png") no-repeat center
      center fixed;
    background-size: 100% 100%;
  }

  .order-header {
    width: 100%;
    margin-bottom: 20px;
    padding-bottom: 15px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    color: #fff;
  }

  .data-provider {
    font-size: 0.9rem;
    color: #fff;
    margin-top: 5px;
  }

  .order-summary {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 25px;
    padding: 5px;
    border-radius: 6px;
  }
  .order-summary > img {
    width: 100%;
  }

  .amount-section {
    text-align: center;
  }

  .amount-display {
    color: #fff;
  }

  .amount-label {
    font-size: 0.9rem;
    color: #fff;
  }

  .status-badge {
    padding: 6px 59px;
    background-color: #0262b0;
    color: white;
    border-radius: 10px;
    font-size: 0.9rem;
    font-weight: bold;
    margin-top: 30px;
  }

  .order-details-grid {
    padding: 10px;
    border: 1px solid #7a797d;
    border-radius: 10px;
    background-color: transparent;
    backdrop-filter: blur(5px);
    -webkit-backdrop-filter: blur(5px);
  }

  .detail-item {
    display: flex;
    justify-content: space-between;
  }

  .detail-label {
    font-size: 0.85rem;
    color: #fff;
    margin-bottom: 5px;
  }

  .detail-value {
    font-size: 1.1rem;
  }

  .foundation-rules {
    margin-top: 15px;
    padding: 10px;
    background-color: transparent;
    backdrop-filter: blur(5px);
    border: 1px solid #7a797d;
    border-radius: 10px;
    -webkit-backdrop-filter: blur(5px);
    text-align-last: center;
    font-size: 0.79rem;
  }

  .rules-content {
    font-size: 0.79rem;
    color: #fff;
    margin-top: 30px;
  }
}
</style>
