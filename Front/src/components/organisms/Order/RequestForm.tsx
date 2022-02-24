import React from "react";
import styled from "@emotion/styled";
import Titlebar from "../../atoms/Titlebar/Titlebar";
import RequestInfoShop from "../../molecules/Order/RequestInfoShop";
import SelectPlastic from "../../molecules/Order/SelectPlastic";

type Type = {
	requirement: string;
};

const OrderForm: React.FC<Type> = ({ requirement }) => {
	return (
		<Wrapper>
			<Titlebar color="#6EC19B">요청사항</Titlebar>
			<RequestInfoShop cachedRequirement={requirement} />
			<SelectPlastic />
		</Wrapper>
	);
};

export default OrderForm;

const Wrapper = styled.div``;
