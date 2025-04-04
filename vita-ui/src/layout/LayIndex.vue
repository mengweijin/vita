<template>
  <el-container>
    <el-header>Header</el-header>
    <el-container>
      <el-aside>Aside</el-aside>
      <el-container>
        <el-main>
          <RouterView />
        </el-main>
        <el-footer>Footer</el-footer>
      </el-container>
    </el-container>
  </el-container>
</template>
