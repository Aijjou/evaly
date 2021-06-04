package aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class LogginAspect {
	
	private final static Log log = LogFactory.getLog(LogginAspect.class);
	
	@Pointcut("execution(* service.*Service.*(..))")
	public void pointCut() {
		
		
	}
	
	@Pointcut("execution(* controller.*Controller.*(..))")
	public void pointCut1() {
		
		
	}
	
	@Pointcut("execution(* config.*Config.*(..))")
	public void pointCut2() {
		
		
	}
	
	
	@Pointcut("execution(* repository.*Repository.*(..))")
	public void pointCut3() {
		
		
	}
	
	
//	@Pointcut("within(fr.afpa.formation.service.DepartmentService*)")
//	public void pointCut2() {
//		
//		
//	}
	
//	@Before("pointCut()")
//	public void adviceBefore() {
//		
//		System.out.println(" ------------------------------------------------------ ");
//		System.out.println(" ----------------------- Before --------------------- ");
//		System.out.println(" ------------------------------------------------------ ");
//	}
//
//	
//	@After("pointCut()")
//	public void adviceAfter(JoinPoint joinPoint) {
//		
//		System.out.println(" ------------------------------------------------------ ");
//		System.out.println(" ----------------------- After --------------------- ");
//		System.out.println(" ----------------------" + joinPoint + "----------------------------- ");
//		
//		for (Object args : joinPoint.getArgs()) {
//			
//			System.out.println(" ---> args = " + args);
//			
//		}
//	}
//	
	
//	@AfterReturning(value="pointCut2(), && pointCut()", returning = "object")
//	public void adviceAfterReturning(JoinPoint joinPoint, Object object) {
//		
//		System.out.println(" ------------------------------------------------------ ");
//		System.out.println(" ----------------------- After returning--------------------- ");
//		
//		if(object instanceof Employee) {
//			System.out.println("return = " + ((Employee) object).toString());
//		}
//		
//		System.out.println("result = " + object);
//	}
//	
	
//	@Around(value="pointCut()")
//	public Object adviceAround(ProceedingJoinPoint joinPoint2) throws Throwable {
//		System.out.println(" ----------------------- Around--------------------- ");
//		System.out.println(" ----------------------- Start--------------------- " + joinPoint2.getSignature().getName());
//		Object reObject = joinPoint2.proceed();
//		System.out.println(" ----------------------- Exit--------------------- " + joinPoint2.getSignature().getName() +" " + reObject );
//		return joinPoint2;
//		
//	}
	
	@Around(value="pointCut() || pointCut1() || pointCut2() || pointCut3()")
	public Object executionTime(ProceedingJoinPoint joinPoint2) throws Throwable {
		
		  long startTime = System.currentTimeMillis();
	        Object object = joinPoint2.proceed();
	        long endtime = System.currentTimeMillis();
	        log.info(" >>>>> Nom de la classe : "+ joinPoint2.getSignature().getDeclaringTypeName() +". Nom de la methode : "+ joinPoint2.getSignature().getName() + ". Temps d'execution est de : " + (endtime-startTime) +"ms");
	        return object;
		
	}
	
	
}
