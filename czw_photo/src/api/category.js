import request from "@/utils/request";
// category

// 获取分类列表
export function GetCategoryList(username) {
    return request({
        url: "/category/getCategories",
        method: "get",
        params: {
            username
        }
    });
}
// 添加分类
export function AddCategory(username,categoryName,data) {
    return request({
        url: "/category/addCategory",
        method: "put",
        params:{
            username,
            categoryName
        },
        data
    });
}

// 根据ID获取分类
export function GetCategoryById(categoryId) {
    return request({
        url: "/category/detail/"+categoryId,
        method: "post"
    });
}
// 根据ID更新分类
export function UpdateCategoryById(id,categoryName,data) {
    return request({
        url: "/category/updateCategory",
        method: "put",
        params:{
            id,
            categoryName
        },
        data
    });
}
// 根据ID删除分类
export function DeleteCategoryById(id) {
    return request({
        url: "/category/deleteCategory/"+id,
        method: "delete"
    });
}