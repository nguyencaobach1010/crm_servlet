$(document).ready(function () {

    $(".btn-delete-job").click(function () {
        var id = $(this).attr("job-id")
        var This = $(this)
        alert("Xóa thành công!!!")
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/CRM/groupwork/delete?id=" + id
        }).done(function (result) {
            This.closest("tr").remove()
            console.log("Ket qua", result)
        })
    })
});