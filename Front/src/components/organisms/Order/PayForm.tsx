import React from "react";
import styled from "@emotion/styled";
import Titlebar from "../../atoms/Titlebar/Titlebar";
import PayInfo from "../../molecules/Order/PayInfo";

const PayForm: React.FC = () => {
	return (
		<Wrapper>
			<Titlebar color="#6EC19B">결제정보</Titlebar>
			<PayInfo />
		</Wrapper>
	);
};

export default PayForm;

const Wrapper = styled.div``;
