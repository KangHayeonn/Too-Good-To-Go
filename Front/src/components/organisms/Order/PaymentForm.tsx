import React from "react";
import styled from "@emotion/styled";
import Titlebar from "../../atoms/Titlebar/Titlebar";
import PaymentInfo from "../../molecules/Order/PaymentInfo";

type Type = {
	paymentMethod: string;
};

const PaymentForm: React.FC<Type> = ({ paymentMethod }) => {
	return (
		<Wrapper>
			<Titlebar color="#6EC19B">결제수단</Titlebar>
			<PaymentInfo cachedPaymentMethod={paymentMethod} />
		</Wrapper>
	);
};

export default PaymentForm;

const Wrapper = styled.div``;
