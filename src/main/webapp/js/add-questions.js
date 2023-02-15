let addBtn = document.getElementById('addTest')
let deleteBtn = document.getElementById('deleteTest')

let maxAmountOfQuestions = 8
let form = document.getElementById('answersDiv')
let count = document.getElementById('numberOfAnswers').value
let answer = document.getElementById('answer').value

if (addBtn) {
    addBtn.addEventListener('click', function(e) {
        e.preventDefault()
        if (count < maxAmountOfQuestions) {
            count++

            let newBlock = document.getElementById("formCheckDiv1")
            let newAnswerDiv = newBlock.cloneNode(true);
            newAnswerDiv.id = `formCheckDiv${count}`;

            newAnswerDiv.childNodes[1].childNodes[1].id = `correct${count}`
            newAnswerDiv.childNodes[1].childNodes[1].name = `correct${count}`
            newAnswerDiv.childNodes[1].childNodes[3].setAttribute('for', `correct${count}`)
            const input = newAnswerDiv.childNodes[3].childNodes[1]

            input.name = `answer${count}`
            input.id = `answer${count}`
            input.value = ``

            newAnswerDiv.childNodes[3].childNodes[3].setAttribute('for', `answer${count}`)
            newAnswerDiv.childNodes[3].childNodes[3].childNodes[0].textContent = `${answer} ${count}`


            form.appendChild(newAnswerDiv)
        }
    })
}

if(deleteBtn) {
    deleteBtn.addEventListener('click',function() {
        addBtn.style.display = 'inline-block';

        if(count > 2) {
            document.querySelector(`#formCheckDiv${count}`).remove();
            count--;
        }
    })
}
