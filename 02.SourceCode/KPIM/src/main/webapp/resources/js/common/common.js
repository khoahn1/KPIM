function convertObjFormdata(formData, data, name) {
	name = name || '';
	if (typeof data === 'object') {
		$.each(data, function(index, value) {
			if (value != null) {
				if (name == '') {
					convertObjFormdata(formData, value, index);
				} else {
					convertObjFormdata(formData, value, name + '.' + index);
				}
			}
		})
	} else {
		formData.append(name, data);
	}
};

//function convertModelToFormData(model, form, namespace = '') {
//    var formData = form || new FormData();
//    var formKey;
//
//    for (var propertyName in model) {
//        if (!model.hasOwnProperty(propertyName) || !model[propertyName]) continue;
//        var formKey = namespace ? `${namespace}[${propertyName}]` : propertyName;
//        if (model[propertyName] instanceof Date)
//            formData.append(formKey, model[propertyName].toISOString());
//        else if (model[propertyName] instanceof Array) {
//            model[propertyName].forEach((element, index) => {
//                const tempFormKey = `${formKey}[${index}]`;
//                convertModelToFormData(element, formData, tempFormKey);
//            });
//        }
//        else if (typeof model[propertyName] === 'object' && !(model[propertyName] instanceof File))
//            convertModelToFormData(model[propertyName], formData, formKey);
//        else
//            formData.append(formKey, model[propertyName].toString());
//    }
//    return formData;
//}

function isNullOrEmpty(value) {
	return (!value || value == undefined || value == "")
}





