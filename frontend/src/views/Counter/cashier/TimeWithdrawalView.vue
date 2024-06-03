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
                 style="background-color: white; width: 100%; max-width: 1200px; min-height: 60%; border: 3px solid lightblue; border-radius: 10px; display: flex; flex-direction: column; justify-content: center; align-items: center; text-align: center;">
              <br>
              <div style="text-align: center;">定期取款</div>
              <br>
              <br>
              <div style="text-align: center;">
                <div style="display: inline-block;">
                  <div>
                    <span>卡号：</span>
                    <el-input v-model="account_id" style="width: 250px;" placeholder="输入您的存款卡号"/>
                  </div>
                  <div>
                    <span>密码：</span>
                    <el-input v-model="password" type="password" style="width: 250px;" placeholder="输入您的密码"/>
                  </div>
                  <br>
                  <div class="mb-4" style="text-align: center;">
                    <el-button type="primary" round @click="find_time_account">查询</el-button>
                  </div>
                </div>
              </div>
              <br>
              <div style="display: flex; justify-content: center;">
                <el-table :data="request_responses" stripe style="width: 1000px;">
                  <el-table-column prop="propertyid" label="定期资产id" width="200px"/>
                  <el-table-column prop="amount" label="金额" width="200px">
                    <template v-slot="{ row = {} }">
                      <span> {{ row.amount }} &nbsp;元&nbsp;</span>
                    </template>
                  </el-table-column>
                  <el-table-column prop="date" label="存入时间" width="200px">
                    <template v-slot="{ row = {} }">
                      <span v-if="row.date === 0">未存入</span>
                      <span v-else>{{ timestampToTime(row.date) }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column prop="length" label="存储时长" width="200px">
                    <template v-slot="{ row = {} }">
                      <span>{{ show_time(row.length) }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column prop="autocontinue" label="是否自动转存" width="200px">
                    <template v-slot="{ row = {} }">
                      <span>{{ change_bool_to_chinese(row.autocontinue) }}</span>
                    </template>
                  </el-table-column>
                </el-table>
              </div>
              <br>
              <br>
              <br>
              <div style="text-align: center;">
                <div style="display: inline-block;">
                  <div>
                    <span>定期资产id:&nbsp;&nbsp;&nbsp;</span>
                    <el-input v-model="propertyid" style="width: 250px;" placeholder="输入您的定期资产id"/>
                  </div>
                  <br>
                  <div class="mb-4" style="text-align: center;">
                    <el-button type="primary" round @click="timeWithdraw">取款</el-button>
                  </div>
                </div>
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
      account_id: '',
      password: '',
      request_responses: [
      //   {
      //   propertyid: '1',
      //   accountid : "",未使用
      //   amount: '10000',
      //   date: '2024/5/29',
      //   length: '1年',
      //   autocontinue: '1',
      //   interestrate :''未使用
      // }
    ],
      propertyid: ''
    }
  },
  methods: {
    exit() {
      this.$router.push('/counter/cashier/login');
    },
    find_time_account() {
      if (this.account_id === '') {
        this.$message.error('账户ID不能为空');
        return;
      }
      if (this.password === '') {
        this.$message.error('密码不能为空');
        return;
      }
      axios.post("/fp/counter/fp/list",null ,{
        params:{
          id: this.account_id,
          password: this.password,
          operatorid: Cookies.get('operatorid')
        }
      }).then(response => {
        this.request_responses = []
        let responses = response.data.payload
        if (responses !== null) {
          responses.forEach(element => {
            this.request_responses.push(element)
        })
        }
      })
    },
    timeWithdraw() {
      if (this.propertyid === '') {
        this.$message.error('资产id不能为空');
        return;
      }
      let flag = 0
      this.request_responses.forEach(element => {
        if (this.propertyid * 1 === element.propertyid) {
          flag = 1;
        }
      })
      if (flag === 1){
        axios.post("/fp/counter/fp/draw",null,{
          params:{
            id : this.account_id,
            password : this.password,
            propertyid : this.propertyid,
            operatorid: Cookies.get('operatorid')
          }
        }).then(response => {
          if(response.data.code === 1){
            this.$message.error(response.data.err);
          }else{
            this.$alert("取款成功，您取出的金额是：" + response.data.payload.toFixed(2) + " 元");
            this.find_time_account()
          }
        })
      }
      else {
        this.$alert("该资产不存在");
      }
      
    },
    timestampToTime(timestamp) {
        var date = new Date(timestamp);
        var Y = date.getFullYear() + '-';
        var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
        var D = (date.getDate()+1 < 10 ? '0'+(date.getDate()) : date.getDate()) + '';
        return Y+M+D;
    },
    show_time(length) {
      if(length < 12) {
        return length + "个月";
      }
      else {
        return length / 12 + "年";
      }
    },
    change_bool_to_chinese(autocontinue) {
      if (autocontinue) {
        return "是";
      }
      else {
        return "否";
      }
    }
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

.el-aside,
.el-menu,
.el-sub-menu {
  background-color: rgb(47, 109, 185) !important;
}

.el-menu-item > a.is-active {
  color: inherit;
  text-decoration: none !important;
}
</style>