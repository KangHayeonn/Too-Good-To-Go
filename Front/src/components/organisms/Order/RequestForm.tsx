import React from "react";
import styled from "@emotion/styled";
import Titlebar from "../../atoms/Titlebar/Titlebar";
import RequestInfoShop from "../../molecules/Order/RequestInfoShop";
import SelectPlastic from "../../molecules/Order/SelectPlastic";

const OrderForm: React.FC = () => {
	return (
		<Wrapper>
			<Titlebar>요청사항</Titlebar>
			<RequestInfoShop />
			<SelectPlastic />
		</Wrapper>
	);
};

export default OrderForm;

const Wrapper = styled.div``;
