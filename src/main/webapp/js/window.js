//todo якось треба зробити, щоб вікно вискакувало лише 1 раз
const body = document.querySelector('body');
console.log("1");
function checkPageStatus() {

    const regex = /isbn=([0-9]+)/;
    const regex2 = /already=(true+)/;
    const match = window.location.search.match(regex);
    const match2 = window.location.search.match(regex2);
    console.log("1");
    if ( match2 !== null) {

        const successPopup = document.createElement('div');
        successPopup.innerHTML = `Ви вже замовили цю книжку`;

        successPopup.style.position = 'fixed';
        successPopup.style.bottom = '20px';
        successPopup.style.right = '-300px';
        successPopup.style.padding = '20px';
        successPopup.style.backgroundColor = '#f63434';
        successPopup.style.border = '1px solid black';
        successPopup.style.zIndex = '9999';


        body.appendChild(successPopup);

        setTimeout(function() {
            successPopup.style.transition = "all 1s ease-in-out";
            successPopup.style.right = "70px";
        }, 300);
        setTimeout(function() {
            successPopup.style.transition = "all 1s ease-in-out";
            successPopup.style.right = "-300px";
        }, 3000);
        setTimeout(function() {
            successPopup.remove();
        }, 5000);
    }
    else if ( match !== null) {

        const isbn = match[1];
        console.log(isbn);
        const successPopup = document.createElement('div');
        successPopup.innerHTML = `Книга з ISBN ${isbn} успішно додана!`;

        successPopup.style.position = 'fixed';
        successPopup.style.bottom = '20px';
        successPopup.style.right = '-300px';
        successPopup.style.padding = '20px';
        successPopup.style.backgroundColor = '#76ad19';
        successPopup.style.border = '1px solid black';
        successPopup.style.zIndex = '9999';


        body.appendChild(successPopup);

        setTimeout(function() {
            successPopup.style.transition = "all 1s ease-in-out";
            successPopup.style.right = "70px";
        }, 300);
        setTimeout(function() {
            successPopup.style.transition = "all 1s ease-in-out";
            successPopup.style.right = "-300px";
        }, 3000);
         setTimeout(function() {
                successPopup.remove();
        }, 5000);
    }
}

document.addEventListener('visibilitychange', checkPageStatus);

checkPageStatus();