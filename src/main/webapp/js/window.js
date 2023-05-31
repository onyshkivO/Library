const body = document.querySelector('body');

function getCookie(cname) {
    let name = cname + "=";
    let counter = 0;
    let ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        counter++;
        console.log(counter);
        let c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return null;
}

function eraseCookie(name) {
    console.log("erase")
    document.cookie = name + '=; Max-Age=0'
}


function checkPageStatus() {
    let isAlready = getCookie("already");
    let isNotAvailable = getCookie("notAvailable");
    let isSuccess = getCookie("success");
    console.log(isAlready);
    console.log(isNotAvailable);
    console.log(isSuccess);

    if (isAlready !== null) {

        const badPopup = document.createElement('div');
        // badPopup.innerHTML = `Ви вже замовили цю книжку`;
        badPopup.innerHTML = `You have already ordered this book`;

        badPopup.style.position = 'fixed';
        badPopup.style.bottom = '20px';
        badPopup.style.right = '-350px';
        badPopup.style.padding = '20px';
        badPopup.style.backgroundColor = '#f63434';
        badPopup.style.border = '1px solid black';
        badPopup.style.zIndex = '9999';


        body.appendChild(badPopup);

        setTimeout(function () {
            badPopup.style.transition = "all 1s ease-in-out";
            badPopup.style.right = "70px";
        }, 300);
        setTimeout(function () {
            badPopup.style.transition = "all 1s ease-in-out";
            badPopup.style.right = "-350px";
        }, 3000);
        setTimeout(function () {
            badPopup.remove();
            eraseCookie("already");
        }, 5000);

    } else if (isNotAvailable !== null) {

        const badPopup = document.createElement('div');
        // badPopup.innerHTML = `Наразі немає в наявності цієї книги `;
        badPopup.innerHTML = `This book is currently out of stock`;
        badPopup.style.position = 'fixed';
        badPopup.style.bottom = '20px';
        badPopup.style.right = '-300px';
        badPopup.style.padding = '20px';
        badPopup.style.backgroundColor = '#f63434';
        badPopup.style.border = '1px solid black';
        badPopup.style.zIndex = '9999';


        body.appendChild(badPopup);

        setTimeout(function () {
            badPopup.style.transition = "all 1s ease-in-out";
            badPopup.style.right = "70px";
        }, 300);
        setTimeout(function () {
            badPopup.style.transition = "all 1s ease-in-out";
            badPopup.style.right = "-300px";
        }, 3000);
        setTimeout(function () {
            badPopup.remove();
            eraseCookie("notAvailable");
        }, 5000);
    } else if (isSuccess !== null) {

        const successPopup = document.createElement('div');
        successPopup.innerHTML = `You have successfully ordered the book!`;

        successPopup.style.position = 'fixed';
        successPopup.style.bottom = '20px';
        successPopup.style.right = '-300px';
        successPopup.style.padding = '20px';
        successPopup.style.backgroundColor = '#76ad19';
        successPopup.style.border = '1px solid black';
        successPopup.style.zIndex = '9999';


        body.appendChild(successPopup);

        setTimeout(function () {
            successPopup.style.transition = "all 1s ease-in-out";
            successPopup.style.right = "70px";
        }, 300);
        setTimeout(function () {
            successPopup.style.transition = "all 1s ease-in-out";
            successPopup.style.right = "-300px";
        }, 3000);
        setTimeout(function () {
            successPopup.remove();
            eraseCookie("success");
        }, 5000);
    }


}

document.addEventListener('visibilitychange', checkPageStatus);

checkPageStatus();