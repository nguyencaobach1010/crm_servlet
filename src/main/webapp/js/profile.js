
$(document).ready(function () {
    var taskId, groupWorkName, taskName, startDate, endDate;

    $(".btn-update").click(function () {
        taskId = $(this).attr("task-id");
        groupWorkName = $(this).attr("group-work-name");
        taskName = $(this).attr("task-name");
        startDate = $(this).attr("task-start-date")
        endDate = $(this).attr("task-end-date")

        var modal = $("#exampleModal");
        modal.find(".modal-body #taskName").text(taskName);
        modal.find(".modal-body #groupWorkName").text(groupWorkName);
        modal.find(".modal-body #start-date").val(startDate)
        modal.find(".modal-body #end-date").val(endDate)
    });

    $(".btn-save-update").click(function () {
        var selectStatus = $("#select-status").val();
        startDate = $("#start-date").val();
        endDate = $("#end-date").val();
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/nvtgroup/user/profile",
            data: {
                taskId: taskId,
                groupWorkName: groupWorkName,
                taskName: taskName,
                startDate: startDate,
                endDate: endDate,
                selectStatus: selectStatus
            }

        }).done(function (result) {
            console.log(result);
            location.reload();
        });
    });
});