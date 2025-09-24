 
    function showFullMessage(fullName, fullMessage) {
        const modal = document.getElementById("reviewModal");
        const modalOverlay = document.getElementById("modalOverlay");

        // Ensure modal is found
        console.log(modal, modalOverlay);
        
        // Update modal content
        modal.querySelector(".review-modal-header").textContent = `Review by ${fullName}`;

        // Format message in chunks of 10 words
        const formattedMessage = fullMessage.split(' ').reduce((acc, word, idx) => {
            return acc + word + ((idx + 1) % 10 === 0 ? "<br>" : " ");
        }, "");

        modal.querySelector(".review-modal-body").innerHTML = formattedMessage;
        modal.style.display = "flex";
        modalOverlay.style.display = "block";
    }


    function closeModal() {
        const modal = document.getElementById("reviewModal");
        const modalOverlay = document.getElementById("modalOverlay");
        modal.style.display = "none";
        modalOverlay.style.display = "none";
    }/**
 * 
 */