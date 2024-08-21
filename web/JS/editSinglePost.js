$(document).ready(function() {
    // Handle click event for Edit button
    $('.btn-edit').on('click', function() {
        var $postItem = $(this).closest('.post-item');
        var $title = $postItem.find('.editable-title');
        var $content = $postItem.find('.pcontent');
        var $code = $postItem.find('.exmp-code');
        var $imageInput = $postItem.find('.postimg input[type="file"]');
        var $saveButton = $postItem.find('.btn-save');
        var $cancelButton = $postItem.find('.btn-cancel');
        var $editButton = $(this);

        // Make elements editable
        $title.attr('contenteditable', 'true');
        $content.attr('contenteditable', 'true');
        $code.attr('contenteditable', 'true');
        
        // Show save and cancel buttons, hide edit button
        $saveButton.show();
        $cancelButton.show();
        $editButton.hide();
        
        // Handle image upload
        $imageInput.on('change', function() {
            // Handle image upload logic here
        });
        
        // Save button logic
        $saveButton.on('click', function() {
            // Perform save logic here
            $title.attr('contenteditable', 'false');
            $content.attr('contenteditable', 'false');
            $code.attr('contenteditable', 'false');
            
            // Hide save and cancel buttons, show edit button
            $saveButton.hide();
            $cancelButton.hide();
            $editButton.show();
        });
        
        // Cancel button logic
        $cancelButton.on('click', function() {
            // Discard changes and reset
            $title.attr('contenteditable', 'false');
            $content.attr('contenteditable', 'false');
            $code.attr('contenteditable', 'false');
            
            // Hide save and cancel buttons, show edit button
            $saveButton.hide();
            $cancelButton.hide();
            $editButton.show();
        });
    });
});
