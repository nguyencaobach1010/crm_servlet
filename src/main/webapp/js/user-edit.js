$(document).ready(function () {

    $(".btn-edit-user").click(function () {
        var id = $(this).attr("user-id")
        var This = $(this)
        alert("Sửa thành công!!!")
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/CRM/user/delete?id=" + id
        }).done(function (result) {
            // This.closest("tr").remove()
            This.window.location.replace("http://localhost:8080/CRM/user");
            console.log("Ket qua", result)
        })
    })
});