/**
 * 
 */

 console.log("this is script file ");

const toggleSidebar = () => {
    if ($(".sidebar").is(":visible")) {
        // Close the sidebar
        $(".sidebar").css("display", "none");
        $(".content").css("margin-left", "0");
    } else {
        // Open the sidebar
        $(".sidebar").css("display", "block");
        // Set the margin-left to the width of your sidebar (e.g., 250px)
        $(".content").css("margin-left", "250px");
    }
};

 