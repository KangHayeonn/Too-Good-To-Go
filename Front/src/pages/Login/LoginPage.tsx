import React from "react";
import PageTemplate from "../../components/templates/PageTemplate";
import Login from "./Login";

const LoginPage: React.FC = () => {
	return (
		<PageTemplate
			isHeader={false}
			section={<Login />}
			isSection
			isFooter={false}
		>
			<></>
		</PageTemplate>
	);
};

export default LoginPage;
