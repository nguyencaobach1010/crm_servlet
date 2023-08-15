$(document).ready(function () {
    $("tbody").on("click", ".btn-delete-groupwork", function () {
        var id = $(this).attr("groupworkid")
        var leaderId = $(this).attr("leaderid")
        var This = $(this)
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/nvtgroup/group-work/delete?groupWorkId=" + id + "&leaderId=" + leaderId
        }).done(function( result ) {
            console.log("Result: " + result)
            if (result === "success") {
                This.closest("tr").remove();
            } else if (result === "errorForbidden") {
                window.location.href = "/nvtgroup/error-forbidden";
            } else {
                window.location.href = "/nvtgroup/error-bad_request";
            }
        });
    })
})