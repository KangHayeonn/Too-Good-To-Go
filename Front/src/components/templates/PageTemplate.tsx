/* eslint-disable react/jsx-props-no-spreading */
import React from "react";
import styled from "@emotion/styled";

const Wrapper = styled.div`
	display: flex;
	flex-direction: column;
	width: 1180px;
	margin: 0 auto;
	padding-top: 92px;
`;

const Header = styled.header`
	display: flex;
	justify-content: center;
	width: 100%;
`;

const Content = styled.section`
	width: 100%;
	box-sizing: border-box;
	margin: 2rem auto;
	border-bottom: 1px solid #eee;
`;

const Footer = styled.footer`
	margin-top: auto;
	width: 100%;
	display: flex;
	justify-content: center;
	padding-bottom: 80px;
`;

type Props = {
	header: React.ReactNode;
	children: React.ReactNode;
	footer?: React.ReactNode;
	isFooter: boolean; // footer 사용 안하시려면 isFooter={false}하시고 footer는 안쓰셔도 됩니다.
};

const PageTemplate: React.FC<Props> = ({
	header,
	children,
	footer,
	isFooter,
	...props
}) => {
	return (
		<Wrapper {...props}>
			<Header>{header}</Header>
			<Content>{children}</Content>
			{isFooter ? <Footer>{footer}</Footer> : null}
		</Wrapper>
	);
};

PageTemplate.defaultProps = {
	footer: null,
};

export default PageTemplate;
