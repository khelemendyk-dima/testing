let testId = document.getElementById('testId').value
let testDuration = document.getElementById('testDuration').value
let finishTime = document.getElementById('finishTime').value
let countDownDate = new Date(finishTime).getTime();

if (testDuration === '0') {
    document.getElementById("timer").innerHTML = "&infin;";
} else if (countDownDate < new Date().getTime()) {
    location.href = "controller?action=search-test&id=" + testId;
} else {
    let countDownFunction = setInterval(function () {
        let now = new Date().getTime();
        let distance = countDownDate - now;

        let hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        let minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        let seconds = Math.floor((distance % (1000 * 60)) / 1000);

        document.getElementById("timer").innerHTML = hours + "h " +
            minutes + "m " + seconds + "s";

        if (distance < 0) {
            clearInterval(countDownFunction);
            let selectedForm = document.getElementById("id");
            selectedForm.submit();
        }
    }, 1000);
}