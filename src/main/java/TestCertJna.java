
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinNT.HANDLE;

import blah.Crypt32;
import blah.Cryptui;
import blah.Wincrypt;
import blah.Wincrypt.*;

public class TestCertJna {
   public static void main(String[] args) {
	   
	   System.out.println("test");
	   
	   // Works
	   HANDLE handle = Crypt32.INSTANCE.CertOpenSystemStore(Pointer.NULL, "MY");
	   
           System.out.println(handle);
           
	   // Works
		CERT_CONTEXT context = Cryptui.INSTANCE.CryptUIDlgSelectCertificateFromStore(handle, 0,
				"", "", 2, 0, null);
		
                System.out.println(context);
                
		CERT_CHAIN_CONTEXT pChainContext = new CERT_CHAIN_CONTEXT();
		CERT_CHAIN_PARA pChainPara = new CERT_CHAIN_PARA();
		
		pChainPara.cbSize = pChainPara.size();
		pChainPara.RequestedUsage.dwType = Wincrypt.USAGE_MATCH_TYPE_AND;
		pChainPara.RequestedUsage.Usage.cUsageIdentifier = 0;
		pChainPara.RequestedUsage.Usage.rgpszUsageIdentifier = null;
		pChainPara.RequestedIssuancePolicy.Usage.cUsageIdentifier = 0;
		pChainPara.RequestedIssuancePolicy.Usage.rgpszUsageIdentifier = null;

		pChainPara.dwUrlRetrievalTimeout = 0;
		pChainPara.fCheckRevocationFreshnessTime = false;
		pChainPara.dwRevocationFreshnessTime = 0;
		pChainPara.pftCacheResync.dwHighDateTime = 0;
		pChainPara.pftCacheResync.dwLowDateTime = 0;

		pChainPara.pStrongSignPara = null;

		// Does not work
		Crypt32.INSTANCE.CertGetCertificateChain(null, context, null, null, pChainPara, 0, null, pChainContext);

                System.out.println(pChainContext);
                
		CERT_CHAIN_POLICY_PARA ChainPolicyPara = new CERT_CHAIN_POLICY_PARA();
		CERT_CHAIN_POLICY_STATUS PolicyStatus = new CERT_CHAIN_POLICY_STATUS();

		ChainPolicyPara.cbSize = ChainPolicyPara.size();
		ChainPolicyPara.dwFlags = 0;

		PolicyStatus.cbSize = PolicyStatus.size();

		// Works
		boolean result = Crypt32.INSTANCE.CertVerifyCertificateChainPolicy(Wincrypt.CERT_CHAIN_POLICY_BASE, pChainContext,
				ChainPolicyPara, PolicyStatus);
		
                System.out.println(result);
                System.out.println(PolicyStatus);
                
		System.out.println("test");
   }
}
