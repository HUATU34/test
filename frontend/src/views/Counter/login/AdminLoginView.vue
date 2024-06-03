<template>
  <div>
    <div class="common-layout">
      <el-container class="layout-container-demo" style="height: 700px">
        <!--标题区域-->
        <el-header
            style="font-size: 30px; background-color: white; font-family: 'Lato', sans-serif; color: rgb(43, 47, 58); line-height: 60px;">
          <div style="display: inline-block;">
            <img src="../icons/logo.png"
                 style=" margin-right: 20px; height: 40px;vertical-align: middle;"/>
          </div>
          <span style="font-size: large; font-family: 'Microsoft YaHei',serif;color: black; font-weight: bold;">线上银行系统--柜台操作系统</span>
        </el-header>
        <el-container>
          <!--侧边栏区域-->
          <el-aside width="200px" style="height: 100vh;">
            <el-scrollbar>
              <el-menu :default-openeds="['1', '3']">
                <el-menu-item index="1-1" style="color: white;">
                  <el-icon>
                    <HomeFilled/>
                  </el-icon>
                  管理员登录
                </el-menu-item>
              </el-menu>
            </el-scrollbar>
          </el-aside>
          <!--主展示区域-->
          <el-main style="background-color: #f1f1f1; display: flex; justify-content: center; align-items: center;">

            <div class="flex gap-4 mb-4"
                 style="background-color: white; width: 60%; max-width: 800px; min-height: 50%; border: 3px solid lightblue; border-radius: 10px; display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center;">
              <span style="font-size: 20px;">管理员登录</span>
              <br>
              <div>
                <span>账号：</span>
                <el-input v-model="admin.id" style="width: 250px" placeholder="请输入管理员id"/>
              </div>
              <br>
              <div>
                <span>密码：</span>
                <el-input type="password" v-model="admin.password" style="width: 250px" placeholder="请输入密码"/>
              </div>
              <br>
              <div class="mb-4" style="text-align: center;">
                <el-button type="primary" round @click="loginAdmin">登录</el-button>
              </div>
              <br>
              <div class="mb-4" style="text-align: right;">
                <router-link to="/counter/cashier/login">
                  <el-button size = "small" type="primary">切换出纳员登录</el-button>
                </router-link>
              </div>
            </div>

          </el-main>

        </el-container>
      </el-container>
    </div>
  </div>
</template>

<script>

import axios from "axios";

export default {
  data() {
    return {
      admin: {
        id: '',
        password: ''
      }
    }
  },
  methods: {
    loginAdmin() {
      // 检查用户名和密码是否为空
      if (this.admin.id === '') {
        this.$message.error('用户id不能为空！');
        return;
      }
      if (this.admin.password === '') {
        this.$message.error('密码不能为空！');
        return;
      }

      //const hashedPassword = CryptoJS.SHA256(this.cashier.password).toString();
      const hashedPassword=this.admin.password;

      axios.post("/admin/counter/admin/login/log",null,{
        params:{
          id:this.admin.id,
          password:hashedPassword
        }
      }).then(response =>{
        if(response.data.code === 1){
          this.$message.error(response.data.err);
          return;
        }else{
          this.$router.push('/counter/admin/cashier');
        }
      })
    },
  },
  mounted() {
  }
}
</script>

<style>
.el-menu-item > a {
  color: inherit;
  text-decoration: none !important;
}

.el-menu-item > a.is-active {
  color: inherit;
  text-decoration: none !important;
}

.el-aside,
.el-menu,
.el-sub-menu {
  background-color: rgb(47, 109, 185) !important;
}

.el-aside .el-menu-item a,
.el-aside .el-sub-menu__title,
.el-aside .el-sub-menu a {
  color: black !important;
}
</style>