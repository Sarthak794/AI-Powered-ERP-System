document.addEventListener("DOMContentLoaded", function () {

    animateCounter("attendanceCount", attendancePercentage, "%");
    animateCounter("attendanceCount2", attendancePercentage, "%");
    animateCounter("totalClassesCount", totalClasses, "");
    animateCounter("presentDaysCount", presentDays, "");

});

function animateCounter(id, target, suffix){

    const element = document.getElementById(id);

    if(!element) return;

    let count = 0;

    const step = target / 40;

    const interval = setInterval(function(){

        count += step;

        if(count >= target){

            count = target;

            clearInterval(interval);

        }

        element.innerHTML = Math.round(count) + suffix;

    },20);

}