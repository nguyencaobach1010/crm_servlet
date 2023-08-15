$(document).ready(function () {
    $(".btn-delete-role").click(function () {
        var id = $(this).attr("roleID")
        var This = $(this)
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/nvtgroup/role/delete?roleID=" + id
        }).done(function( result ) {
            This.closest("tr").remove()
        });
    })
})