import React from "react";
import "./Footer.css";

const Footer: React.FC = () => {
	return (
		<div className="bottom">
			<ul className="links">
				<li className="privacy">
					<a href="/page/serviveagreement">(주) 투굿투고</a>
				</li>
				<li className="copyright">
					<a href="/">© HAYEON & SANGWON & JIHOON & JAEWON & SUHO</a>
				</li>
			</ul>
		</div>
	);
};

export default Footer;
