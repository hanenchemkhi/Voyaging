function searchEntry(searchBox, searchTable, index) {
  let input, filter, table, tr, td, i, txtValue;
  input = document.getElementById(searchBox);
  filter = input.value.toUpperCase();
  table = document.getElementById(searchTable);
  tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[index];
    if (td) {
      txtValue = td.textContent || td.innerText;
      if (txtValue.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }       
  }
}
 function validatePassword(){
    let passwordInput = document.getElementById("password");

    let lowerCaseLetters = /[a-z]/g;
    let upperCaseLetters = /[A-Z]/g;
    let numbers = /[0-9]/g;


    if(passwordInput.value.length <8){
      document.getElementById("invalidPassword").style.display ="block"
    }
    else{

      if(passwordInput.value.match(numbers) && passwordInput.value.match(lowerCaseLetters)&& passwordInput.value.match(upperCaseLetters) ){
        document.getElementById("invalidPassword").style.display ="none"
        
          
      
    }
  }
}

function confirmPassword(){
  let passwordInput = document.getElementById("password").value;
  let confirmPasswordInput = document.getElementById("confirmPSW").value;

  if(passwordInput==confirmPasswordInput){
    document.getElementById("invalidconfirmPassword").style.display ="none"
    
  }else{
   
    document.getElementById("invalidconfirmPassword").style.display ="block"
  }

}

function validateEmail(){
  let emailInput = document.getElementById("email").value;
  let mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
if(emailInput.match(mailformat)){
  document.getElementById("invalidEmail").style.display ="none"
 
}else{
  
  document.getElementById("invalidEmail").style.display ="block"
}}

function validateCreditCardNumber(){
  let ccnInput = document.getElementById("cc-number").value;
  if(ccnInput[0] == 3 && ccnInput.length==15){
    document.getElementById("invalidccn").style.display ="none";

  }else{if(ccnInput[0]!=3 && ccnInput.length==16){document.getElementById("invalidccn").style.display ="none";}
  else{document.getElementById("invalidccn").style.display ="block"}}
  
}

function validateCardExpDate(){
 
  let exDate = document.getElementById("cc-expiration").value;
  let todayDate = new Date();
  if(exDate.length == 5  ){
    
    let exMonth = exDate.slice(0,2);
    let exYear = exDate.slice(3,5);
    
    experationDate = new Date();
    experationDate.setFullYear(exYear, exMonth,1);
    
    if(Date.parse(experationDate)>= Date.parse(todayDate)){
      
      document.getElementById("invalidcc-experation").style.display ="none";
    }
  }else{
    document.getElementById("invalidcc-experation").style.display ="block";

  }

}

function validateCheckin(){
  let checkinDate = document.getElementById("checkinInput").value;
  let todayDate = new Date();
  if(Date.parse(checkinDate)>= Date.parse(todayDate)){
    document.getElementById("invalidCheckin").style.display ="none";
  }else{
    document.getElementById("invalidCheckin").style.display ="block";
  }

}

function validateCheckout(){

  let checkinDate = document.getElementById("checkinInput").value;
  let checkoutDate = document.getElementById("checkoutInput").value;  
  let todayDate = new Date();

  if(Date.parse(checkoutDate)>= Date.parse(todayDate) && Date.parse(checkoutDate)>= Date.parse(checkinDate)){
    document.getElementById("invalidCheckout").style.display ="none";
  }else{
    document.getElementById("invalidCheckout").style.display ="block";
  }
}

function validateRoom(){
  let noRooms = document.getElementById("roomsDataList").value;
  if(noRooms <=3 && noRooms >0){
    document.getElementById("invalidRooms").style.display="none";
  }else{
    document.getElementById("invalidRooms").style.display="block";
  }
}

function validateGuests(){
  let noGuests = document.getElementById("guestsDataList").value;
  let noRooms = document.getElementById("roomsDataList").value;
  if(noRooms <=3 && noRooms >0){
    let maxGuests = noRooms * 4;
    if(noGuests <=maxGuests && noGuests >0){
      document.getElementById("invalidGuests").style.display="none";
    }else{
      document.getElementById("invalidGuests").style.display="block";
    }}
    else{
      document.getElementById("invalidGuests").style.display="block";
    }
}
