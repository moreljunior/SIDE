/**
 * SIDE root namespace.
 * 
 * @namespace SIDE
 */
// Ensure SIDE root object exists
if (typeof SIDE == "undefined" || !SIDE) {
	var SIDE = {};
}
if (console == undefined) {
	// create a fake console object to avoid error (console is provided by
	// firebug)
	var console = {
		log : function(msg) {
		}
	};
}

(function() {

	/**
	 * Create a select field from a datasource
	 * 
	 * @class SIDE.MyDSSelectField
	 * @extends inputEx.SelectField
	 * @constructor
	 * @param {Object}
	 *            options Added options:
	 *            <ul>
	 *            <li>options: list of option elements configurations</li>
	 *            <li>datasource: the datasource</li>
	 *            <li>valueKey: value key</li>
	 *            <li>labelKey: label key</li>
	 *            </ul>
	 * @param {String}
	 *            initialValue value to select on widget creation
	 * 
	 */
	SIDE.MyDSSelectField = function(options, initialValue) {
		SIDE.MyDSSelectField.superclass.constructor.call(this, options);
		this.initialValue = initialValue;
		this.log("DSS initial value :" + initialValue);
	};

	YAHOO.lang.extend(SIDE.MyDSSelectField, inputEx.DSSelectField, {
		log : function(msg) {
			console.log("[SIDE.MyDSSelectField] " + msg);
		},
		/**
		 * Callback for request success
		 */
		onDatasourceSuccess : function(oRequest, oParsedResponse, oPayload) {
			this.populateSelect(oParsedResponse.results);
			this.addChoice({
				value : '',
				label : '',
				position : 0,
				selected : false
			});
			this.log("dataloaded");
			if (this.initialValue) {
				this.log("dataloaded init old: (value setted by populateSelect)" + this.getValue());
				this.setValue(this.initialValue);
				this.log("dataloaded init new:" + this.getValue());
			} else {
				this.setValue('');
			}
			this.log("force previousState to 'valid'");
			this.previousState = 'valid';
		}
	});

	// Register this class as "sidedsselect" type
	inputEx.registerType("sidedsselect", SIDE.MyDSSelectField);

}());