$(document).ready(function () {
    var taskId, groupWorkName, taskName, startDate, endDate, leaderId, userId;

    $("tbody").on("click", ".btn-update-task", function () {
        taskId = $(this).attr("task-id");
        groupWorkName = $(this).attr("group-work-name");
        taskName = $(this).attr("task-name");
        startDate = $(this).attr("task-start-date")
        endDate = $(this).attr("task-end-date")
        leaderId = $(this).attr("leaderId")
        userId = $(this).attr("user-id")

        const modal = $("#taskModal");
        modal.find(".modal-body #taskName").text(taskName);
        modal.find(".modal-body #groupWorkName").text(groupWorkName);
        modal.find(".modal-body #start-date").val(startDate)
        modal.find(".modal-body #end-date").val(endDate)
        modal.find(".modal-body #leaderID").val(leaderId)
        modal.find(".modal-body #user-id").val(userId)
    });

    $(".btn-save-update").click(function () {
        var selectStatus = $("#select-status").val();
        startDate = $("#start-date").val();
        endDate = $("#end-date").val();
        leaderId = $("#leaderID").val();
        userId = $("#user-id").val()
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/nvtgroup/task/update",
            data: {
                taskId: taskId,
                groupWorkName: groupWorkName,
                taskName: taskName,
                startDate: startDate,
                endDate: endDate,
                selectStatus: selectStatus,
                leaderId: leaderId,
                userId: userId
            }

        }).done(function (result) {
            if (result === "success") {
                location.reload();
            } else if (result === "errorForbidden") {
                window.location.href = "/nvtgroup/error-forbidden";
            } else {
                window.location.href = "/nvtgroup/error-bad_request";
            }
        });
    });
})

$(document).ready(function () {
    $("tbody").on("click", ".btn-delete-task", function () {
        var taskId = $(this).attr("taskId")
        var leaderId = $(this).attr("leaderId")
        var This = $(this)

        $.ajax({
            method: "GET",
            url: `http://localhost:8080/nvtgroup/task/delete?leaderId=${leaderId}&taskId=${taskId}`
        }).done(function (result) {
            if (result === "success") {
                This.closest("tr").remove()
                //console.log("Result" + result)
            } else if (result === "errorForbidden") {
                window.location.href = "/nvtgroup/error-forbidden";
            } else if (result === "errorBadRequest") {
                window.location.href = "/nvtgroup/error-bad_request";
            }
        });
    })
})