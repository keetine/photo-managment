import request from "@/utils/request";
// photos

// 上传图片
export function UploadPhoto(categoryId,address,username,data) {
    return request({
        url: "/photos/upload",
        method: "post",
        params: {
            categoryId,
            address,
            username
        },
        data,
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    });
}

// 获取所有照片数量
export function GetPhotoCount() {
    return request({
        url: "/photos/getPhotosCount",
        method: "get"
    });
}

// 获取照片分页
export function GetPhotosByRange(start, end, categoryId) {
    return request({
        url: "/photos/getPhotosByRange",
        method: "get",
        params: {
            start,
            end,
            categoryId
        }
    });
}
// 获取所有照片分页
export function GetAllPhotosByRange(start, end) {
    return request({
        url: "/photos/getAllPhotosByRange",
        method: "get",
        params: {
            start,
            end
        }
    });
}

//首页 获取所有照片
export function GetPhotos() {
    return request({
        url: "/photos/getAllPhotos",
        method: "get"
    });
}
// 删除图片
export function DeletePhoto(ids) {
    // data是一个数组
    return request({
        url: "/photos/delete",
        method: "delete",
        data: ids
    })
}

// 通过分类id获取分类名
export function GetCategoryNameById(id) {
    return request({
        url: "/photos/getCategoryNameById/" + id,
        method: "get",
    })
}