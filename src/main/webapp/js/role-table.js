$(document).ready(function () {

    $(".btn-delete-role").click(function () {
        var id = $(this).attr("role-id")
        var This = $(this)
        alert("Xóa thành công!!!")
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/CRM/role/delete?id=" + id
        }).done(function (result) {
            This.closest("tr").remove()
            console.log("Ket qua", result)
        })
    })
});