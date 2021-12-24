import React from "react";
import styled from "@emotion/styled";
import Titlebar from "../../atoms/Titlebar/Titlebar";
import PaymentInfo from "../../molecules/Order/PaymentInfo";

const PaymentForm: React.FC = () => {
	return (
		<Wrapper>
			<Titlebar color="#6EC19B">결제수단</Titlebar>
			<PaymentInfo />
		</Wrapper>
	);
};

export default PaymentForm;

const Wrapper = styled.div``;
