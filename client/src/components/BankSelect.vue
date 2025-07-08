<template>
  <div class="bank-select">
    <input
      type="text"
      class="input"
      v-model="inputValue"
      @focus="showDropdown = true"
      @input="filterList"
      @blur="onBlur"
      :placeholder="placeholder"
      :disabled="props.show"
    />
    <ul v-if="showDropdown" class="dropdown">
      <li
        v-for="(item, index) in filteredOptions"
        :key="index"
        @mousedown.prevent="selectItem(item)"
      >
        {{ item }}
      </li>
    </ul>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";

const props = defineProps({
  modelValue: String,
  options: {
    type: Array,
    required: true,
  },
  show: {
    type: Boolean,
  },
  placeholder: {
    type: String,
    default: "请选择银行",
  },
});

const emit = defineEmits(["update:modelValue"]);

const inputValue = ref(props.modelValue || "");
const showDropdown = ref(false);
const filteredOptions = ref([...props.options]);

watch(
  () => props.modelValue,
  (val) => {
    inputValue.value = val;
  }
);

function filterList() {
  const keyword = inputValue.value.toLowerCase();
  filteredOptions.value = props.options.filter((item) =>
    item.toLowerCase().includes(keyword)
  );
}

function selectItem(item) {
  inputValue.value = item;
  emit("update:modelValue", item);
  showDropdown.value = false;
}

function onBlur() {
  // 延迟关闭确保 click 事件生效
  setTimeout(() => {
    showDropdown.value = false;
  }, 100);
}
</script>

<style scoped>
.bank-select {
  position: relative;
  width: 100%;
}

.input {
  width: 100%;
  border: 1px solid transparent;
  border-radius: 6px;
  color: #fff;
  box-sizing: border-box;
  background: transparent;
}

.dropdown {
  position: absolute;
  top: 100%;
  left: 0;
  right: 0;
  max-height: 200px;
  overflow-y: auto;
  border: 1px solid #ccc;
  background: white;
  z-index: 10;
  border-radius: 6px;
  margin-top: 2px;
  padding: 0;
  list-style: none;
}

.dropdown li {
  padding: 10px;
  cursor: pointer;
}

.dropdown li:hover {
  background-color: #f5f5f5;
}

@media screen and (min-width: 768px) {
  .bank-select {
    position: relative;
    width: 100%;
  }

  .input {
    width: 100%;
    border: 1px solid transparent;
    border-radius: 6px;
    color: #fff;
    box-sizing: border-box;
    background: transparent;
  }

  .dropdown {
    position: absolute;
    top: 100%;
    left: 0;
    right: 0;
    max-height: 200px;
    overflow-y: auto;
    border: 1px solid #ccc;
    background: white;
    z-index: 10;
    border-radius: 6px;
    margin-top: 2px;
    padding: 0;
    list-style: none;
  }

  .dropdown li {
    padding: 10px;
    cursor: pointer;
  }

  .dropdown li:hover {
    background-color: #f5f5f5;
  }
}
</style>
