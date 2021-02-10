/**
 * 
 */
package io.mosip.registration.util.control;

import java.util.List;

import io.mosip.kernel.core.logger.spi.Logger;
import io.mosip.registration.audit.AuditManagerService;
import io.mosip.registration.config.AppConfig;
import io.mosip.registration.constants.RegistrationConstants;
import io.mosip.registration.context.SessionContext;
import io.mosip.registration.controller.reg.DemographicDetailController;
import io.mosip.registration.dto.RegistrationDTO;
import io.mosip.registration.dto.UiSchemaDTO;
import javafx.scene.Node;
import javafx.scene.control.Label;
import static io.mosip.registration.constants.RegistrationConstants.APPLICATION_ID;
import static io.mosip.registration.constants.RegistrationConstants.APPLICATION_NAME;

/**
 * 
 * Control Type will give high level controls for fields TextField,CheckBox,
 * DropDown,DropDown, Buttons, document type, Biometric Type
 * 
 * It also provides a features to copy,disable and visible
 * 
 * @author YASWANTH S
 *
 */
public abstract class FxControl extends Node {

	protected static final Logger LOGGER = AppConfig.getLogger(DemographicDetailController.class);
	private static final String loggerClassName = "AbstractControlType";

	protected UiSchemaDTO uiSchemaDTO;

	protected FxControl control;

	public Node node;

	protected DemographicDetailController demographicDetailController;
	protected AuditManagerService auditFactory;

	public void refreshFields() {

	}

	/**
	 * Build Error code, title and fx Element Set Listeners Set Actione events
	 * 
	 * @param uiSchemaDTO field information
	 * @param parentPane  field to be placed
	 */
	public abstract FxControl build(UiSchemaDTO uiSchemaDTO);

	/**
	 * Copy the value from source node to target nodes
	 * 
	 * @param srcNode     copy from
	 * @param targetNodes copy to
	 */
	public abstract void copyTo(Node srcNode, List<Node> targetNodes);

	/**
	 * 
	 * Set Data into Registration DTO
	 * 
	 * @param data value
	 */
	public abstract void setData(Object data);

	/**
	 * 
	 * Fill Data into fx element
	 * 
	 * @param data value
	 */
	public abstract void fillData(Object data);

	/**
	 * Get Value from fx element
	 * 
	 * @return Value
	 */
	public abstract Object getData();

	/**
	 * Check value is valid or not
	 * 
	 * @return boolean is valid or not
	 */
	public abstract boolean isValid(Node node);

	/**
	 * Disable the field
	 */
	public void disable(Node node, boolean isDisable) {

		node.setDisable(isDisable);

	}

	/**
	 * Hide the field
	 */
	public void visible(Node node, boolean isVisible) {

		node.setVisible(isVisible);

	}

	public UiSchemaDTO getUiSchemaDTO() {
		return uiSchemaDTO;
	}

	public abstract void setListener(Node node);

	public Node getNode() {
		return this.node;
	}

	protected String getMandatorySuffix(UiSchemaDTO schema) {
		String mandatorySuffix = RegistrationConstants.EMPTY;
		RegistrationDTO registrationDTO = getRegistrationDTo();
		String categeory = registrationDTO.getRegistrationCategory();
		switch (categeory) {
		case RegistrationConstants.PACKET_TYPE_UPDATE:
			if (registrationDTO.getUpdatableFields().contains(schema.getId())) {
				mandatorySuffix = schema.isRequired() ? RegistrationConstants.ASTRIK : RegistrationConstants.EMPTY;
			}
			break;

		case RegistrationConstants.PACKET_TYPE_NEW:
			mandatorySuffix = schema.isRequired() ? RegistrationConstants.ASTRIK : RegistrationConstants.EMPTY;
			break;
		}
		return mandatorySuffix;
	}

	protected Label getLabel(String id, String titleText, String styleClass, boolean isVisible, double prefWidth) {
		/** Field Title */
		Label label = new Label();
		label.setId(id);
		label.setText(titleText);
		label.getStyleClass().add(styleClass);
		label.setVisible(isVisible);
		label.setPrefWidth(prefWidth);
		return label;
	}

	protected Node getField(String id) {
		return node.lookup(RegistrationConstants.HASH + id);
	}

	protected RegistrationDTO getRegistrationDTo() {
		RegistrationDTO registrationDTO = null;
		if (SessionContext.map() != null || !SessionContext.map().isEmpty()) {
			registrationDTO = (RegistrationDTO) SessionContext.map().get(RegistrationConstants.REGISTRATION_DATA);
		}
		return registrationDTO;
	}

}
