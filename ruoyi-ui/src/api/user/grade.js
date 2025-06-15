import request from '@/utils/request'

// 查询用户等级列表
export function listGrade(query) {
  return request({
    url: '/system/grade/list',
    method: 'get',
    params: query
  })
}

// 查询用户等级详细
export function getGrade(id) {
  return request({
    url: '/system/grade/' + id,
    method: 'get'
  })
}

// 新增用户等级
export function addGrade(data) {
  return request({
    url: '/system/grade',
    method: 'post',
    data: data
  })
}

// 修改用户等级
export function updateGrade(data) {
  return request({
    url: '/system/grade',
    method: 'put',
    data: data
  })
}

// 删除用户等级
export function delGrade(id) {
  return request({
    url: '/system/grade/' + id,
    method: 'delete'
  })
}
