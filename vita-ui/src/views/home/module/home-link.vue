<script setup>
const openBlankUrl = (url) => {
  window.open(url, '_blank');
};

onMounted(() => {

});
</script>

<template>
  <div class="vt-home-link">
    <div>链接</div>

    <div class="vt-home-link-content">

      <div class="vt-home-link-item">
        <el-button type="primary" class="vt-home-link-item-button" @click="openBlankUrl('https://aday.fun')">
          <img src="/favicon.svg" style="width: 30px; height: 30px;" /> &nbsp;个人博客</el-button>
      </div>

      <div class="vt-home-link-item">
        <el-button type="success" class="vt-home-link-item-button" @click="openBlankUrl('https://vita.aday.fun')">
          <img src="/favicon.svg" style="width: 30px; height: 30px;" /> &nbsp;Vita 在线演示</el-button>
      </div>

      <div class="vt-home-link-item">
        <el-button type="info" class="vt-home-link-item-button"
          @click="openBlankUrl('https://github.com/mengweijin/vita')">
          <el-icon :size="30">
            <Icon icon="ri:github-fill"></Icon>
          </el-icon> &nbsp;
          Vita Github
        </el-button>
      </div>

      <div class="vt-home-link-item">
        <el-button type="danger" class="vt-home-link-item-button"
          @click="openBlankUrl('https://gitee.com/mengweijin/vita')">
          <el-icon :size="30">
            <Icon icon="simple-icons:gitee"></Icon>
          </el-icon> &nbsp;Vita Gitee 码云
        </el-button>
      </div>
    </div>
  </div>
</template>


<style lang="css" scoped>
.vt-home-link {
  background-color: white;
  margin-top: 15px;
  padding: 10px 15px 10px 15px;
}

.vt-home-link-content {
  margin: 10px 0 5px 0;
}

.vt-home-link-item+.vt-home-link-item {
  margin-top: 15px;
}

.vt-home-link-item-button {
  width: 100%;
  height: 50px;
  line-height: 50px;
  font-size: 18px;
}
</style>
