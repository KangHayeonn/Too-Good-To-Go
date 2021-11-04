/* eslint-disable react/jsx-props-no-spreading */
import React from "react";
import styled from "@emotion/styled";

const Wrapper = styled.div`
	display: flex;
	flex-direction: column;
	width: 1200px;
	margin: 0 auto;
	padding-top: 90px;
`;

const Header = styled.header`
	display: flex;
`;

const Content = styled.section`
	width: 100%;
	box-sizing: border-box;
	margin: 2rem auto;
`;

const Footer = styled.footer`
	margin-top: auto;
`;

type Props = {
	header: any;
	children: any;
	footer: any;
};

const PageTemplate: React.FC<Props> = ({
	header,
	children,
	footer,
	...props
}) => {
	return (
		<Wrapper {...props}>
			<Header>{header}</Header>
			<Content>{children}</Content>
			<Footer>{footer}</Footer>
		</Wrapper>
	);
};

export default PageTemplate;
