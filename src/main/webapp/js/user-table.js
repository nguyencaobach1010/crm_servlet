$(document).ready(function () {
    $("tbody").on("click", ".btn-delete-user", function () {
        var id = $(this).attr("userID")
        var This = $(this)
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/nvtgroup/user/delete?userID=" + id
        }).done(function( result ) {
            This.closest("tr").remove()
            console.log("Result" + result)
        });
    })
})