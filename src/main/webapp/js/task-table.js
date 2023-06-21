$(document).ready(function () {

    $(".btn-delete-task").click(function () {
        var id = $(this).attr("task-id")
        var This = $(this)
        alert("Xóa thành công!!!")
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/CRM/user/delete?id=" + id
        }).done(function (result) {
            This.closest("tr").remove()
            console.log("Ket qua", result)
        })
    })
});