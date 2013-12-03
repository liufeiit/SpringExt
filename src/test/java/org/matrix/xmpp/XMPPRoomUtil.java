package org.matrix.xmpp;

import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.Affiliate;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.SubjectUpdatedListener;

/**
 * XMPP多人聊天室工具。
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013-11-15 上午10:52:45
 */
public class XMPPRoomUtil {
	public static final String MUC_ROOMCONFIG_ROOMADMINS = "muc#roomconfig_roomadmins";
	public static final String MUC_ROOMCONFIG_PASSWORDPROTECTEDROOM = "muc#roomconfig_passwordprotectedroom";
	public static final String MUC_ROOMCONFIG_MODERATEDROOM = "muc#roomconfig_moderatedroom";
	public static final String MUC_ROOMCONFIG_PUBLICROOM = "muc#roomconfig_publicroom";
	public static final String MUC_ROOMCONFIG_MAXUSERS = "muc#roomconfig_maxusers";
	public static final String MUC_ROOMCONFIG_CHANGESUBJECT = "muc#roomconfig_changesubject";
	public static final String MUC_ROOMCONFIG_ROOMDESC = "muc#roomconfig_roomdesc";
	public static final String MUC_ROOMCONFIG_ROOMNAME = "muc#roomconfig_roomname";
	public static final String MUC_ROOMCONFIG_PRESENCEBROADCAST = "muc#roomconfig_presencebroadcast";
	public static final String X_MUC_ROOMCONFIG_REGISTRATION = "x-muc#roomconfig_registration";
	public static final String X_MUC_ROOMCONFIG_CANCHANGENICK = "x-muc#roomconfig_canchangenick";
	public static final String X_MUC_ROOMCONFIG_RESERVEDNICK = "x-muc#roomconfig_reservednick";
	public static final String MUC_ROOMCONFIG_ENABLELOGGING = "muc#roomconfig_enablelogging";
	public static final String MUC_ROOMCONFIG_WHOIS = "muc#roomconfig_whois";
	public static final String MUC_ROOMCONFIG_ALLOWINVITES = "muc#roomconfig_allowinvites";
	public static final String MUC_ROOMCONFIG_MEMBERSONLY = "muc#roomconfig_membersonly";
	public static final String MUC_ROOMCONFIG_PERSISTENTROOM = "muc#roomconfig_persistentroom";
	public static final String MUC_ROOMCONFIG_ROOMOWNERS = "muc#roomconfig_roomowners";
	public static final String HTTP_JABBER_ORG_PROTOCOL_MUC_ROOMCONFIG = "http://jabber.org/protocol/muc#roomconfig";
	public static final String FORM_TYPE = "FORM_TYPE";
	public static final Log log = LogFactory.getLog(XMPPRoomUtil.class);

	public static boolean createMultiRoom(XMPPConnection connection, String userName) {
		try {
			String adminName = getName(connection);
			String roomName = adminName + "@conference." + connection.getServiceName();
			
			log.error(String.format(
					"Create MultiRoom [ConnectionID: %s, Host: %s, Port: %s,ServiceName: %s,User: %s, admin: %s, room: %s, owner: %s].", 
					connection.getConnectionID(),
					connection.getHost(),
					connection.getPort(),
					connection.getServiceName(),
					connection.getUser(),
					adminName,
					roomName,
					userName
					));
			
			MultiUserChat muc = new MultiUserChat(connection, roomName);
			muc.create(adminName);
			Form form = muc.getConfigurationForm();
			Form submitForm = form.createAnswerForm();

			FormField field = new FormField(FORM_TYPE);

			field.setType(FormField.TYPE_HIDDEN);
			field.addValue(HTTP_JABBER_ORG_PROTOCOL_MUC_ROOMCONFIG);
			submitForm.addField(field);

			for (Iterator<FormField> fields = form.getFields(); fields.hasNext();) {
				FormField defaultField = fields.next();
				if (!FormField.TYPE_HIDDEN.equals(defaultField.getType()) && defaultField.getVariable() != null) {
					submitForm.setDefaultAnswer(defaultField.getVariable());
				}
			}

			submitForm.setAnswer(MUC_ROOMCONFIG_ROOMOWNERS, Arrays.asList(adminName + "@" + connection.getServiceName()));
			submitForm.setAnswer(MUC_ROOMCONFIG_ROOMADMINS, Arrays.asList(userName + "@" + connection.getServiceName()));
			submitForm.setAnswer(MUC_ROOMCONFIG_PERSISTENTROOM, true);
			submitForm.setAnswer(MUC_ROOMCONFIG_MEMBERSONLY, false);
			submitForm.setAnswer(MUC_ROOMCONFIG_ALLOWINVITES, true);
			submitForm.setAnswer(MUC_ROOMCONFIG_ENABLELOGGING, true);
			submitForm.setAnswer(X_MUC_ROOMCONFIG_RESERVEDNICK, true);
			submitForm.setAnswer(X_MUC_ROOMCONFIG_CANCHANGENICK, false);
			submitForm.setAnswer(X_MUC_ROOMCONFIG_REGISTRATION, false);
			
			submitForm.setAnswer(MUC_ROOMCONFIG_ROOMNAME, userName + "'s room");
//			submitForm.setAnswer(MUC_ROOMCONFIG_WHOIS, "moderator");

			field = new FormField(MUC_ROOMCONFIG_PRESENCEBROADCAST);
			field.addValues(Arrays.asList("moderator", "participant", "visitor"));
			submitForm.addField(field);

			field = new FormField(MUC_ROOMCONFIG_WHOIS);
			field.addValue("moderator");
			submitForm.addField(field);

			field = new FormField(MUC_ROOMCONFIG_ROOMNAME);
			field.addValue(userName);
			submitForm.addField(field);

			field = new FormField(MUC_ROOMCONFIG_MAXUSERS);
			field.addValue("30");
			submitForm.addField(field);

			field = new FormField(MUC_ROOMCONFIG_ROOMDESC);
			field.addValue(userName + "-room");
			submitForm.addField(field);

			boolean changeSubject = true;
			field = new FormField(MUC_ROOMCONFIG_CHANGESUBJECT);
			field.addValue((changeSubject) ? "1" : "0");
			submitForm.addField(field);

			boolean publicRoom = true;
			field = new FormField(MUC_ROOMCONFIG_PUBLICROOM);
			field.addValue((publicRoom) ? "1" : "0");
			submitForm.addField(field);

			boolean persistentRoom = true;
			field = new FormField(MUC_ROOMCONFIG_PERSISTENTROOM);
			field.addValue((persistentRoom) ? "1" : "0");
			submitForm.addField(field);

			boolean moderatedRoom = true;
			field = new FormField(MUC_ROOMCONFIG_MODERATEDROOM);
			field.addValue((moderatedRoom) ? "1" : "0");
			submitForm.addField(field);

			boolean membersOnly = true;
			field = new FormField(MUC_ROOMCONFIG_MEMBERSONLY);
			field.addValue((membersOnly) ? "1" : "0");
			submitForm.addField(field);

			boolean allowInvites = true;
			field = new FormField(MUC_ROOMCONFIG_ALLOWINVITES);
			field.addValue((allowInvites) ? "1" : "0");
			submitForm.addField(field);

			boolean password = false;
			field = new FormField(MUC_ROOMCONFIG_PASSWORDPROTECTEDROOM);
			field.addValue((password) ? "1" : "0");
			submitForm.addField(field);

			boolean enableLog = true;
			field = new FormField(MUC_ROOMCONFIG_ENABLELOGGING);
			field.addValue((enableLog) ? "1" : "0");
			submitForm.addField(field);

			boolean reservedNick = true;
			field = new FormField(X_MUC_ROOMCONFIG_RESERVEDNICK);
			field.addValue((reservedNick) ? "1" : "0");
			submitForm.addField(field);

			boolean canChangeNick = true;
			field = new FormField(X_MUC_ROOMCONFIG_CANCHANGENICK);
			field.addValue((canChangeNick) ? "1" : "0");
			submitForm.addField(field);

			boolean registrationEnabled = true;
			field = new FormField(X_MUC_ROOMCONFIG_REGISTRATION);
			field.addValue((registrationEnabled) ? "1" : "0");
			submitForm.addField(field);

			field = new FormField(MUC_ROOMCONFIG_ROOMADMINS);
			for (Affiliate affiliate : muc.getAdmins()) {
				field.addValue(affiliate.getJid());
			}
			submitForm.addField(field);

			field = new FormField(MUC_ROOMCONFIG_ROOMOWNERS);
			for (Affiliate affiliate : muc.getOwners()) {
				field.addValue(affiliate.getJid());
			}
			field.addValue(userName);
			submitForm.addField(field);

			muc.sendConfigurationForm(submitForm);
			log.error("Create MultiRoom for user " + userName + " success.");
			muc.addMessageListener(new PacketListener() {
				@Override
				public void processPacket(Packet packet) {
//					log.error(GsonUtil.gson.toJson(packet));
				}
			});
			muc.addSubjectUpdatedListener(new SubjectUpdatedListener() {
				@Override
				public void subjectUpdated(String subject, String from) {
					log.error("Room Subject Updated to " + subject + " by " + from);
				}
			});
			return true;
		} catch (XMPPException e) {
			log.error("Create MultiRoom for user " + userName + " error.", e);
		}
		return false;
	}

	private static String getName(XMPPConnection connection) {
		return connection.getUser().split("@")[0];
	}
}