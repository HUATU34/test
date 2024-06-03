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
          <el-aside width="200px" style="height: 87vh; display: flex; flex-direction: column;">
            <el-scrollbar style="flex: 1">
              <el-menu :default-openeds="['1', '2']">
                <el-sub-menu index="1">
                  <template #title>
                    <el-icon style="color: white;">
                      <UserFilled/>
                    </el-icon>
                    <span style="color: white;">账户管理</span>
                  </template>

                  <el-menu-item index="1-1">
                    <router-link to="/counter/cashier/openAccount">
                      <el-icon style="color: white;">
                        <HomeFilled/>
                      </el-icon>
                      <span style="color: white;">开设账户</span>
                    </router-link>
                  </el-menu-item>

                  <el-menu-item index="1-2">
                    <router-link to="/counter/cashier/lossAndReissue">
                      <el-icon style="color: white;">
                        <WalletFilled/>
                      </el-icon>
                      <span style="color: white;">挂失/补发</span>
                    </router-link>
                  </el-menu-item>

                  <el-menu-item index="1-3">
                    <router-link to="/counter/cashier/freeAndUnfreeze">
                      <el-icon style="color: white;">
                        <Promotion/>
                      </el-icon>
                      <span style="color: white;">冻结/解冻</span>
                    </router-link>
                  </el-menu-item>
                </el-sub-menu>

                <el-sub-menu index="2">
                  <template #title>
                    <el-icon style="color: white;">
                      <UserFilled/>
                    </el-icon>
                    <span style="color: white;">账户操作</span>
                  </template>

                  <el-menu-item index="2-1">
                    <router-link to="/counter/cashier/currentDeposit">
                      <el-icon style="color: white;">
                        <HomeFilled/>
                      </el-icon>
                      <span style="color: white;">活期存款</span>
                    </router-link>
                  </el-menu-item>

                  <el-menu-item index="2-2">
                    <router-link to="/counter/cashier/currentWithdrawal">
                      <el-icon style="color: white;">
                        <WalletFilled/>
                      </el-icon>
                      <span style="color: white;">活期取款</span>
                    </router-link>
                  </el-menu-item>

                  <el-menu-item index="2-3">
                    <router-link to="/counter/cashier/timeDeposit">
                      <el-icon style="color: white;">
                        <Promotion/>
                      </el-icon>
                      <span style="color: white;">定期存款</span>
                    </router-link>
                  </el-menu-item>

                  <el-menu-item index="2-4">
                    <router-link to="/counter/cashier/timeWithdrawal">
                      <el-icon style="color: white;">
                        <Shop/>
                      </el-icon>
                      <span style="color: white;">定期取款</span>
                    </router-link>
                  </el-menu-item>

                  <el-menu-item index="2-5">
                    <router-link to="/counter/cashier/transferAccount">
                      <el-icon style="color: white;">
                        <List/>
                      </el-icon>
                      <span style="color: white;">转账</span>
                    </router-link>
                  </el-menu-item>

                </el-sub-menu>
              </el-menu>
            </el-scrollbar>
            <el-button type="danger" @click="exit" style="display: block; margin: auto;">
              退出登录
            </el-button>
          </el-aside>

          <!--主展示区域-->
          <el-main style="background-color: #f1f1f1; display: flex; justify-content: center; align-items: center;">
            <div class="flex gap-4 mb-4"
                 style="background-color: white; width: 60%; max-width: 800px; min-height: 60%; border: 3px solid lightblue; border-radius: 10px; display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center;">
              <br>
              <div style="text-align: center;">挂失/补发</div>
              <br>
              <div>
                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;卡号：&nbsp;&nbsp;&nbsp;</span>
                <el-input v-model="id" style="width: 250px" placeholder="输入您的卡号" />
              </div>
              <br>
              <div>
                <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;密码：&nbsp;&nbsp;&nbsp;</span>
                <el-input v-model="password" type="password" style="width: 250px" placeholder="输入您的密码" />
              </div>
              <br>
              <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作：&nbsp;&nbsp;
                <el-select v-model="selected" style="width: 250px;">
                  <el-option label="挂失" value=1></el-option>
                  <el-option label="补发" value=2></el-option>
                  <el-option label="取消挂失" value=3></el-option>
                </el-select>
              </span>
              <br>
              <div v-if="this.selected === '2'">
                <span>选择补发类型：&nbsp;&nbsp;
                  <el-select v-model="cardType" style="width: 250px;">
                    <el-option label="卡片" value='Card'></el-option>
                    <el-option label="存折" value='Book'></el-option>
                  </el-select>
                </span>
              </div>
              <br>
              <div class="mb-4" style="text-align: center;">
                <el-button type="primary" round @click="ensure">确认</el-button>
              </div>
              <br>
            </div>
          </el-main>

        </el-container>
      </el-container>
    </div>
  </div>
</template>

<script>

import axios from "axios";
import Cookies from "js-cookie";

export default {
  data() {
    return {
      id: '',
      password: '',
      cardType: '',
      selected:''
    }
  },
  methods: {
    exit() {
      this.$router.push('/counter/cashier/login');
    },
    ensure() {
      if (this.id === '') {
        this.$message.error('卡号不能为空');
        return;
      }
      if (this.password === '') {
        this.$message.error('密码不能为空');
        return;
      }
      if (this.selected === '') {
        this.$message.error('操作选项不能为空');
        return;
      }

      if(this.selected==='1'){
        axios.post("/account/counter/account/modify/status/lost",null,{
          params:{
            cardid: this.id,
            password: this.password,
            operatorid: Cookies.get('operatorid')
          }
        }).then(response => {
          if(response.data.code === 1){
            this.$message.error(response.data.err);
          }else{
            this.$message.success('挂失成功');
          }
        })
      }
      else if(this.selected==='3'){
        axios.post("/account/counter/account/modify/status/unlost",null,{
          params:{
            cardid: this.id,
            password: this.password,
            operatorid: Cookies.get('operatorid')
          }
        }).then(response => {
          if(response.data.code === 1){
            this.$message.error(response.data.err);
          }else{
            this.$message.success('取消挂失成功');
          }
        })
      }
      else if (this.selected==='2') {
        if (this.cardType==='') {
          this.$message.error('类型不能为空');
          return;
        }
        axios.post("/account/counter/account/modify/replace",null,{
          params:{
            cardid: this.id,
            password: this.password,
            cardtype: this.cardType,
            operatorid: Cookies.get('operatorid')
          }
        }).then(response => {
          if(response.data.code === 1){
            this.$message.error(response.data.err);
            this.$alert("您的账户未挂失，无法补发");
          }else{
            this.$alert('补发成功' + response.data.payload);
          }
        })
      }
    },
  },
  mounted() {
  }
}
</script>

<style>
.el-menu-item>a {
  color: inherit;
  text-decoration: none !important;
}
.el-aside,
.el-menu,
.el-sub-menu {
  background-color: rgb(47, 109, 185) !important;
}

.el-menu-item>a.is-active {
  color: inherit;
  text-decoration: none !important;
}
</style>