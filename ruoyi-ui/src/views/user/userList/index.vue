<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
<!--      <el-form-item label="用户名" prop="userName">-->
<!--        <el-input-->
<!--          v-model="queryParams.userName"-->
<!--          placeholder="请输入用户名"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
<!--      </el-form-item>-->
      <el-form-item label="账号" prop="loginAccount">
        <el-input
          v-model="queryParams.loginAccount"
          placeholder="请输入账号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:user:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:user:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:user:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:user:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="邀请码" align="center" prop="invitationCode" />
      <el-table-column label="账号" align="center" prop="loginAccount">
        <template slot-scope="scope">
          {{ scope.row.loginAccount }}<br />
          <span :style="{ color: scope.row.registerType === '0' ? 'red' : 'green' }">
          {{ scope.row.registerType == '0' ? '虚拟的' : scope.row.registerType == '1' ? '真实的' : '未知' }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="等级" align="center" prop="levelName" />
      <el-table-column label="电话号码" align="center" prop="phoneNumber" />
      <el-table-column label="提现地址" align="center" prop="withdrawalAddress" />
      <el-table-column label="账户余额" align="center" prop="accountBalance" />
      <el-table-column label="邀请人姓名" align="center" prop="inviterName" />
<!--      <el-table-column label="状态 1:启用 0:禁用" align="center" prop="status" />-->
      <el-table-column label="是否禁用" align="center" prop="status">
        <template slot-scope="scope">
          <el-switch
              v-model="scope.row.status"
              :active-value="1"
              :inactive-value="0"
              @change="handleStatusChange(scope.row)"
          />
        </template>
      </el-table-column>

      <el-table-column label="银行名称" align="center" prop="bankName" />
      <el-table-column label="银行账户名称" align="center" prop="bankAccountName" />
      <el-table-column label="银行账号" align="center" prop="bankAccountNumber" />
      <el-table-column label="当天刷单数量" align="center" prop="brushNumber" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:user:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:user:remove']"
          >删除</el-button>

          <el-button
              size="mini"
              type="text"
              icon="el-icon-refresh"
              @click="handleRegisterType(scope.row)"
          >转为{{ scope.row.registerType === "0" ? '客人' : '员工' }}</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改用户对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="账号" prop="loginAccount">
          <el-input v-model="form.loginAccount" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="等级id" prop="levelId">
<!--          <el-input v-model="form.levelId" placeholder="请输入等级id" />-->

          <el-select v-model="form.levelId" placeholder="请选择">
            <el-option
                v-for="item in gradeList"
                :key="item.id"
                :label="item.gradeName"
                :value="item.id">
            </el-option>
          </el-select>
        </el-form-item>



        <el-form-item label="登录密码" prop="loginPassword">
          <el-input
              v-model="form.loginPassword"
              :type="JudgingStatus ? 'text' : 'password'"
              placeholder="请输入登录密码"
          />
        </el-form-item>

        <el-form-item label="资金密码" prop="fundPassword">
          <el-input
              v-model="form.fundPassword"
              :type="JudgingStatus ? 'text' : 'password'"
              placeholder="请输入资金密码"
          />
        </el-form-item>



        <el-form-item label="类型" prop="registerType">
          <!--          <el-input v-model="form.registerType" placeholder="请输入用户类型" />-->
          <el-select v-model="form.registerType" placeholder="请选择">
            <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="电话号码" prop="phoneNumber">
          <el-input v-model="form.phoneNumber" placeholder="请输入电话号码" />
        </el-form-item>
        <el-form-item label="邀请人代码" prop="inviterCode">
          <el-input v-model="form.inviterCode" placeholder="请输入邀请人代码" />
        </el-form-item>
        <el-form-item label="提现地址" prop="withdrawalAddress">
          <el-input v-model="form.withdrawalAddress" placeholder="请输入提现地址" />
        </el-form-item>



        <el-form-item label="银行名称" prop="bankName">
          <el-input v-model="form.bankName" placeholder="请输入银行名称" />
        </el-form-item>
        <el-form-item label="银行账户名称" prop="bankAccountName">
          <el-input v-model="form.bankAccountName" placeholder="请输入银行账户名称" />
        </el-form-item>
        <el-form-item label="银行账号" prop="bankAccountNumber">
          <el-input v-model="form.bankAccountNumber" placeholder="请输入银行账号" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {setStatus, listUser, getUser, delUser, addUser, updateUser, setRegisterType} from "@/api/user/user"
import { listGrade } from "@/api/user/grade"

export default {
  name: "User",
  data() {
    return {
      options: [{
        value: '1',
        label: '客人'
      }, {
        value: '0',
        label: '员工'
      }],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      JudgingStatus:true,
      // 用户表格数据
      userList: [],
      gradeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        levelId: null,
        loginAccount: null,
        loginPassword: null,
        fundPassword: null,
        withdrawalAddress: null,
        registerType: null,
        phoneNumber: null,
        phoneNumberType: null,
        accountBalance: null,
        invitationCode: null,
        inviter: null,
        inviterCode: null,
        inviterName: null,
        status: null,
        bankName: null,
        bankAccountName: null,
        bankAccountNumber: null,
        higherUid: null,
        lastLoginIp: null,
        lastLoginIpAddress: null,
        deleteStatus: null,
        brushNumber: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        phoneNumber: [
          { required: true, message: "电话号码不能为空", trigger: "blur" }
        ],
        registerType: [
          { required: true, message: "用户类型不能为空", trigger: "blur" }
        ],

        levelId: [
          { required: true, message: "等级id不能为空", trigger: "blur" }
        ],
        loginAccount: [
          { required: true, message: "账号不能为空", trigger: "blur" }
        ],
        loginPassword: [
          { required: true, message: "登录密码不能为空", trigger: "blur" }
        ],
        fundPassword: [
          { required: true, message: "资金密码不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态 1:启用 0:禁用不能为空", trigger: "change" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
    this.getGradeList()
  },
  methods: {
    handleRegisterType(row) {
      const currentType = row.registerType === "0" ? "员工" : "客人";
      const targetType = row.registerType === "0" ? "客人" : "员工";
      const newTypeValue = row.registerType === "0" ? "1" : "0";

      this.$confirm(
          `是否确认将该用户从【${currentType}】改为【${targetType}】？`,
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }
      ).then(() => {
        // 切换 registerType 的值
        row.registerType = newTypeValue;

        // 可选：发送 API 请求更新数据库
        this.updateRegisterType(row);

        this.$message.success(`已将用户类型修改为【${targetType}】`);
      }).catch(() => {
        this.$message.info('操作取消');
      });
    },
    updateRegisterType(e){
      setRegisterType(e.uid).then(response=>{
        if(response.code === 200){
          this.getList()
        }
      })
    },
    handleStatusChange(e){
      setStatus(e.uid).then(response=>{
        if(response.code === 200){
          this.getList()
        }
      })
      console.log(e)
    },
    getGradeList() {
      this.loading = true
      listGrade().then(response => {
        this.gradeList = response.rows
      })
    },
    /** 查询用户列表 */
    getList() {
      this.loading = true
      listUser(this.queryParams).then(response => {
        this.userList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.JudgingStatus = true
      this.form = {
        uid: null,
        levelId: null,
        loginAccount: null,
        loginPassword: null,
        fundPassword: null,
        withdrawalAddress: null,
        registerType: null,
        phoneNumber: null,
        phoneNumberType: null,
        accountBalance: null,
        invitationCode: null,
        inviter: null,
        inviterCode: null,
        inviterName: null,
        status: null,
        bankName: null,
        bankAccountName: null,
        bankAccountNumber: null,
        higherUid: null,
        lastLoginIp: null,
        lastLoginIpAddress: null,
        deleteStatus: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        brushNumber: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.uid)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.JudgingStatus = true
      this.title = "添加用户"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const uid = row.uid || this.ids
      getUser(uid).then(response => {
        this.form = response.data
        this.open = true
        this.JudgingStatus = false

        this.title = "修改用户"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.uid != null) {
            updateUser(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addUser(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const uids = row.uid || this.ids
      this.$modal.confirm('是否确认删除用户编号为"' + uids + '"的数据项？').then(function() {
        return delUser(uids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/user/export', {
        ...this.queryParams
      }, `user_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
