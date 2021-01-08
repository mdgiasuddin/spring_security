package com.example.spring_security_demo.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JsonParsingService {

    public void parseJson() {
        String xml = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                "<soapenv:Envelope xmlns:soapenv=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                "    <soapenv:Header/>\n" +
                "    <soapenv:Body>\n" +
                "        <ns:GetCbsCustomerInfoResponse xmlns:ns=\"http://ws.apache.org/axis2\">\n" +
                "            <ns:return xmlns:ax219=\"http://fi/xsd\" xmlns:ax221=\"http://city/xsd\" xmlns:ax223=\"http://Dormant.Activation.Account.fi/xsd\" xmlns:ax225=\"http://UnFreeze.Account.fi/xsd\" xmlns:ax227=\"http://AcctLien.fi/xsd\" xmlns:ax229=\"http://Freeze.Account.fi/xsd\" xmlns:ax231=\"http://Waive.Chequebook.fi/xsd\" xmlns:ax234=\"http://Scheme.fi/xsd\" xmlns:ax236=\"http://Update.Corporate.Customer.fi/xsd\" xmlns:ax238=\"http://Corporate.Customer.fi/xsd\" xmlns:ax243=\"http://Custom.Account.fi/xsd\" xmlns:ax245=\"http://Acknowledgement.Chequebook.fi/xsd\" xmlns:ax248=\"http://Create.Corporate.Customer.fi/xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"ax219:GetCbsCustomerResponse\">\n" +
                "                <ax219:bBKSegCode xsi:nil=\"true\"/>\n" +
                "                <ax219:birthDate>1982-09-12T00:00:00.000</ax219:birthDate>\n" +
                "                <ax219:branchCode>101</ax219:branchCode>\n" +
                "                <ax219:childChannelId xsi:nil=\"true\"/>\n" +
                "                <ax219:childChannelType xsi:nil=\"true\"/>\n" +
                "                <ax219:city>DHAKA</ax219:city>\n" +
                "                <ax219:constitutionCode>IND</ax219:constitutionCode>\n" +
                "                <ax219:currencyCode>BDT</ax219:currencyCode>\n" +
                "                <ax219:custStatus>NOR</ax219:custStatus>\n" +
                "                <ax219:customerId>CB1401933</ax219:customerId>\n" +
                "                <ax219:customerName>FAISAL AHMEDD</ax219:customerName>\n" +
                "                <ax219:defaultAddrType>Mailing</ax219:defaultAddrType>\n" +
                "                <ax219:employmentStatus>SPORTSMAN</ax219:employmentStatus>\n" +
                "                <ax219:entityDocDetailsResponses xsi:type=\"ax219:EntityDocDetailsResponse\">\n" +
                "                    <ax219:countryOfIssue>BD</ax219:countryOfIssue>\n" +
                "                    <ax219:docCode>CHAC</ax219:docCode>\n" +
                "                    <ax219:entityDocumentID>3007121</ax219:entityDocumentID>\n" +
                "                    <ax219:entityType>CIFRetCust</ax219:entityType>\n" +
                "                    <ax219:expireDt xsi:nil=\"true\"/>\n" +
                "                    <ax219:identificationType>CRTVH</ax219:identificationType>\n" +
                "                    <ax219:isDocumentVerified></ax219:isDocumentVerified>\n" +
                "                    <ax219:issueDt>2020-03-16T00:00:00.000</ax219:issueDt>\n" +
                "                    <ax219:placeOfIssue xsi:nil=\"true\"/>\n" +
                "                    <ax219:preferredUniqueId></ax219:preferredUniqueId>\n" +
                "                    <ax219:referenceNum>DSA</ax219:referenceNum>\n" +
                "                    <ax219:status>Received</ax219:status>\n" +
                "                    <ax219:typeCode>IDENTIFICATION PROOF</ax219:typeCode>\n" +
                "                    <ax219:typeDesc>IDENTIFICATION</ax219:typeDesc>\n" +
                "                </ax219:entityDocDetailsResponses>\n" +
                "                <ax219:fatherName>ABDUL WAHEDAAAAAAAAA</ax219:fatherName>\n" +
                "                <ax219:gender>M</ax219:gender>\n" +
                "                <ax219:guardianName>MD. HAYDER HOSSEN</ax219:guardianName>\n" +
                "                <ax219:isCorpRepresentative>N</ax219:isCorpRepresentative>\n" +
                "                <ax219:isDummy>N</ax219:isDummy>\n" +
                "                <ax219:isEBankingEnabled>N</ax219:isEBankingEnabled>\n" +
                "                <ax219:isMinor>N</ax219:isMinor>\n" +
                "                <ax219:isNRE>Y</ax219:isNRE>\n" +
                "                <ax219:isNegated>N</ax219:isNegated>\n" +
                "                <ax219:isSMSBankingEnabled>N</ax219:isSMSBankingEnabled>\n" +
                "                <ax219:isWAPBankingEnabled>N</ax219:isWAPBankingEnabled>\n" +
                "                <ax219:maritalStatus>SINGL</ax219:maritalStatus>\n" +
                "                <ax219:monitoringRMCode>80371</ax219:monitoringRMCode>\n" +
                "                <ax219:motherName>JABUN NAHAR</ax219:motherName>\n" +
                "                <ax219:nameOfEmployer>GANER VHOBON</ax219:nameOfEmployer>\n" +
                "                <ax219:nameOfIntroducer>MOHAMMAD SHAMSUZZAMAN</ax219:nameOfIntroducer>\n" +
                "                <ax219:nationalId>19852693014967589</ax219:nationalId>\n" +
                "                <ax219:nationality>BD</ax219:nationality>\n" +
                "                <ax219:occupationCode>E</ax219:occupationCode>\n" +
                "                <ax219:pan></ax219:pan>\n" +
                "                <ax219:passportDtls>GOVERNMENT OF BANGLADESH</ax219:passportDtls>\n" +
                "                <ax219:passportExpDt>2021-10-19T00:00:00.000</ax219:passportExpDt>\n" +
                "                <ax219:passportIssueDt>2020-03-16T00:00:00.000</ax219:passportIssueDt>\n" +
                "                <ax219:passportNum>222222222</ax219:passportNum>\n" +
                "                <ax219:phoneEmailInfoResponses xsi:type=\"ax219:PhoneEmailInfoResponse\">\n" +
                "                    <ax219:emailInfo></ax219:emailInfo>\n" +
                "                    <ax219:phoneEmailID>5944428</ax219:phoneEmailID>\n" +
                "                    <ax219:phoneEmailType>COMMPH2</ax219:phoneEmailType>\n" +
                "                    <ax219:phoneNum>+8845653457651</ax219:phoneNum>\n" +
                "                    <ax219:phoneNumCountryCode>88</ax219:phoneNumCountryCode>\n" +
                "                    <ax219:phoneNumLocalCode>45653457651</ax219:phoneNumLocalCode>\n" +
                "                    <ax219:phoneOrEmail>PHONE</ax219:phoneOrEmail>\n" +
                "                    <ax219:prefFlag>N</ax219:prefFlag>\n" +
                "                </ax219:phoneEmailInfoResponses>\n" +
                "                <ax219:phoneEmailInfoResponses xsi:type=\"ax219:PhoneEmailInfoResponse\">\n" +
                "                    <ax219:emailInfo></ax219:emailInfo>\n" +
                "                    <ax219:phoneEmailID>5944429</ax219:phoneEmailID>\n" +
                "                    <ax219:phoneEmailType>WORKPH1</ax219:phoneEmailType>\n" +
                "                    <ax219:phoneNum>+8834562345676</ax219:phoneNum>\n" +
                "                    <ax219:phoneNumCountryCode>88</ax219:phoneNumCountryCode>\n" +
                "                    <ax219:phoneNumLocalCode>34562345676</ax219:phoneNumLocalCode>\n" +
                "                    <ax219:phoneOrEmail>PHONE</ax219:phoneOrEmail>\n" +
                "                    <ax219:prefFlag>N</ax219:prefFlag>\n" +
                "                </ax219:phoneEmailInfoResponses>\n" +
                "                <ax219:phoneEmailInfoResponses xsi:type=\"ax219:PhoneEmailInfoResponse\">\n" +
                "                    <ax219:emailInfo>FAISALBDDU@GMAIL.COM</ax219:emailInfo>\n" +
                "                    <ax219:phoneEmailID>2704423</ax219:phoneEmailID>\n" +
                "                    <ax219:phoneEmailType>COMMEML</ax219:phoneEmailType>\n" +
                "                    <ax219:phoneNum>01719365358</ax219:phoneNum>\n" +
                "                    <ax219:phoneNumCountryCode></ax219:phoneNumCountryCode>\n" +
                "                    <ax219:phoneNumLocalCode></ax219:phoneNumLocalCode>\n" +
                "                    <ax219:phoneOrEmail>EMAIL</ax219:phoneOrEmail>\n" +
                "                    <ax219:prefFlag>Y</ax219:prefFlag>\n" +
                "                </ax219:phoneEmailInfoResponses>\n" +
                "                <ax219:prefMiscInqDtlsList xsi:type=\"ax219:PrefMiscInqDtls\">\n" +
                "                    <ax219:dbFloat1>0.0</ax219:dbFloat1>\n" +
                "                    <ax219:dbFloat2>0.0</ax219:dbFloat2>\n" +
                "                    <ax219:dbFloat3>0.0</ax219:dbFloat3>\n" +
                "                    <ax219:dbFloat4>0.0</ax219:dbFloat4>\n" +
                "                    <ax219:dbFloat5>0.0</ax219:dbFloat5>\n" +
                "                    <ax219:dtDate1>2020-10-10T00:00:00.000</ax219:dtDate1>\n" +
                "                    <ax219:miscellaneousid>8885247</ax219:miscellaneousid>\n" +
                "                    <ax219:strText10>DEM</ax219:strText10>\n" +
                "                    <ax219:type>CURRENCY</ax219:type>\n" +
                "                </ax219:prefMiscInqDtlsList>\n" +
                "                <ax219:prefMiscInqDtlsList xsi:type=\"ax219:PrefMiscInqDtls\">\n" +
                "                    <ax219:dbFloat1>0.0</ax219:dbFloat1>\n" +
                "                    <ax219:dbFloat2>0.0</ax219:dbFloat2>\n" +
                "                    <ax219:dbFloat3>0.0</ax219:dbFloat3>\n" +
                "                    <ax219:dbFloat4>0.0</ax219:dbFloat4>\n" +
                "                    <ax219:dbFloat5>0.0</ax219:dbFloat5>\n" +
                "                    <ax219:dtDate1>2020-10-10T00:00:00.000</ax219:dtDate1>\n" +
                "                    <ax219:miscellaneousid>8885232</ax219:miscellaneousid>\n" +
                "                    <ax219:strText10>AED</ax219:strText10>\n" +
                "                    <ax219:type>CURRENCY</ax219:type>\n" +
                "                </ax219:prefMiscInqDtlsList>\n" +
                "                <ax219:relationship xsi:nil=\"true\"/>\n" +
                "                <ax219:responseCode>100</ax219:responseCode>\n" +
                "                <ax219:responseMessage>Operation Successful.</ax219:responseMessage>\n" +
                "                <ax219:retCustAddressInfoResponses xsi:type=\"ax219:RetCustAddressInfoResponse\">\n" +
                "                    <ax219:addrCategory>Future/Onsite</ax219:addrCategory>\n" +
                "                    <ax219:addrLine1>TEST ADDRESS</ax219:addrLine1>\n" +
                "                    <ax219:addrLine2>TEST ADDRESS</ax219:addrLine2>\n" +
                "                    <ax219:addrLine3></ax219:addrLine3>\n" +
                "                    <ax219:addrStartDt>2020-03-16T00:00:00.000</ax219:addrStartDt>\n" +
                "                    <ax219:addressID>14034085</ax219:addressID>\n" +
                "                    <ax219:city>DHAKA</ax219:city>\n" +
                "                    <ax219:cityCode></ax219:cityCode>\n" +
                "                    <ax219:country>BD</ax219:country>\n" +
                "                    <ax219:countryCode></ax219:countryCode>\n" +
                "                    <ax219:endDt>2099-12-31T00:00:00.000</ax219:endDt>\n" +
                "                    <ax219:holdMailFlag>N</ax219:holdMailFlag>\n" +
                "                    <ax219:isAddressVerified>N</ax219:isAddressVerified>\n" +
                "                    <ax219:name xsi:nil=\"true\"/>\n" +
                "                    <ax219:postalCode>1212</ax219:postalCode>\n" +
                "                    <ax219:prefAddr>N</ax219:prefAddr>\n" +
                "                    <ax219:prefFormat>FREE_TEXT_FORMAT</ax219:prefFormat>\n" +
                "                    <ax219:salutationCode></ax219:salutationCode>\n" +
                "                    <ax219:state>DHAKA</ax219:state>\n" +
                "                    <ax219:stateCode></ax219:stateCode>\n" +
                "                    <ax219:town>DHAKA</ax219:town>\n" +
                "                </ax219:retCustAddressInfoResponses>\n" +
                "                <ax219:retCustAddressInfoResponses xsi:type=\"ax219:RetCustAddressInfoResponse\">\n" +
                "                    <ax219:addrCategory>Future/OnSite</ax219:addrCategory>\n" +
                "                    <ax219:addrLine1>TEST ADDRESS</ax219:addrLine1>\n" +
                "                    <ax219:addrLine2>TEST ADDRESS</ax219:addrLine2>\n" +
                "                    <ax219:addrLine3></ax219:addrLine3>\n" +
                "                    <ax219:addrStartDt>2020-03-16T00:00:00.000</ax219:addrStartDt>\n" +
                "                    <ax219:addressID>14036412</ax219:addressID>\n" +
                "                    <ax219:city>DHAKA</ax219:city>\n" +
                "                    <ax219:cityCode></ax219:cityCode>\n" +
                "                    <ax219:country>BD</ax219:country>\n" +
                "                    <ax219:countryCode></ax219:countryCode>\n" +
                "                    <ax219:endDt>2099-12-31T00:00:00.000</ax219:endDt>\n" +
                "                    <ax219:holdMailFlag>N</ax219:holdMailFlag>\n" +
                "                    <ax219:isAddressVerified>N</ax219:isAddressVerified>\n" +
                "                    <ax219:name xsi:nil=\"true\"/>\n" +
                "                    <ax219:postalCode>1212</ax219:postalCode>\n" +
                "                    <ax219:prefAddr>N</ax219:prefAddr>\n" +
                "                    <ax219:prefFormat>FREE_TEXT_FORMAT</ax219:prefFormat>\n" +
                "                    <ax219:salutationCode></ax219:salutationCode>\n" +
                "                    <ax219:state>DHAKA</ax219:state>\n" +
                "                    <ax219:stateCode></ax219:stateCode>\n" +
                "                    <ax219:town>DHAKA</ax219:town>\n" +
                "                </ax219:retCustAddressInfoResponses>\n" +
                "                <ax219:rmCode>UBSADMIN</ax219:rmCode>\n" +
                "                <ax219:salutation>MR.</ax219:salutation>\n" +
                "                <ax219:segmentationClass>NA</ax219:segmentationClass>\n" +
                "                <ax219:senCitizenApplicableDate>2042-09-12T00:00:00.000</ax219:senCitizenApplicableDate>\n" +
                "                <ax219:seniorCitizen>N</ax219:seniorCitizen>\n" +
                "                <ax219:shortName>FAISALD</ax219:shortName>\n" +
                "                <ax219:spouseName>KHANDAKER ZOHRA ISLAM</ax219:spouseName>\n" +
                "                <ax219:staffEmployeeID>2012391</ax219:staffEmployeeID>\n" +
                "                <ax219:staffFlag>Y</ax219:staffFlag>\n" +
                "                <ax219:startDt>2021-01-07T00:00:00.000</ax219:startDt>\n" +
                "                <ax219:status>A</ax219:status>\n" +
                "                <ax219:statusOfIntroducer>EXC</ax219:statusOfIntroducer>\n" +
                "                <ax219:subSegment>MIGR</ax219:subSegment>\n" +
                "            </ns:return>\n" +
                "        </ns:GetCbsCustomerInfoResponse>\n" +
                "    </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        JSONObject xmlJsonObject = XML.toJSONObject(xml);

        System.out.println(xmlJsonObject.toString(4));
    }
}
