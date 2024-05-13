/**
 * 
import {
   putResponses
} from './PROmpt.js'
*/

var enhancedPrompt = ""

document.getElementById("myForm").addEventListener("submit", function (event) {
   // Prevent default form submission
   event.preventDefault();

   // Get form values
   var prompt = document.getElementById("prompt").value;
   var typeOfPrompt = document.getElementById("typeOfPrompt").value;
   var detail = document.getElementById('detail').value;
   var additional = document.getElementById("additionalInputCode").value;
   var targetLanguage = document.getElementById("targetLanguage").value;



   var typeExtras = {
      detail: detail
   };

   var outputHTML = `
        <h2>Form Data</h2>
        <p><strong>Prompt:</strong> ${prompt}</p>
        <p><strong>Type of prompt:</strong> ${typeOfPrompt ? "Yes" : "No"}</p>
        <p><strong>Level of detail:</strong> ${detail}</p>
    `;
   if (additional != "") {
      outputHTML += `
      <p><strong>Additional:</strong> ${additional}</p>
  `;
      typeExtras.additional = additional
   }

   if (targetLanguage != "") {
      outputHTML += `
      <p><strong>TargetLanguage:</strong> ${targetLanguage}</p>
  `;
      typeExtras.targetLanguage = targetLanguage
   }
   // Update the output div with the form data
   document.getElementById("output").innerHTML = outputHTML;




   // Stringify the type object separately
   var typeString = JSON.stringify(typeExtras);

   // Construct formData object including the stringified type
   var formData = {
      prompt: prompt,
      type: typeOfPrompt, // Include the stringified type
      typeExtras: typeString
   };

   var formDataString = JSON.stringify(formData);
   console.log(formDataString)
   fetch('http://localhost:8080/api/enhanceUserPrompt', {
         method: 'POST',
         headers: {
            'Content-Type': 'application/json'
         },
         body: formDataString
      })
      .then(response => response.json()) // Assuming backend responds with text
      .then(data => {

         console.log('Success:', data);
         enhancedPrompt = data.text
         document.getElementById("output").innerHTML += `<p><strong>Finished prompt:</strong></p>
         <p>${enhancedPrompt}</p>`;
      })
      .catch((error) => {
         console.error('Error:', error);
      });

   //var chatGPTresponse = putResponses(enhancedPrompt, typeOfPrompt)
   //console.log(chatGPTresponse)
})

document.getElementById("typeOfPrompt").addEventListener("change", function () {
   var countrySelect = document.getElementById("typeOfPrompt");
   var selectedCountry = countrySelect.value;

   // Check if the selected option is one that should trigger additional elements
   if (selectedCountry === "code") {
      document.getElementById("additionalElements").style.display = "block";
   } else {
      document.getElementById("additionalElements").style.display = "none";
   }
});

document.getElementById("additionalInputCode").addEventListener("change", function () {
   var additional = document.getElementById("additionalInputCode");
   var selectedOption = additional.value;

   // Check if the selected option is "translate"
   if (selectedOption === "translate") {
      // If "translate" is selected, display the extra div
      document.getElementById("translateExtra").style.display = "block";
   } else {
      // If another option is selected, hide the extra div
      document.getElementById("translateExtra").style.display = "none";
   }
});